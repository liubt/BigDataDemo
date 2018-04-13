## 服务说明

系统分为三个微服务，goods（商品），order（订单），inventory（库存）。goods服务可以查询商品的基本信息。order服务负责接受订单，在产生订单之前要去inventory服务中查询并扣除库存。扣除成功，订单才能生成。

## 幂等
#### 正常流程
0. order服务接到请求后，生成订单编号。然后同步调用inventory服务扣库存。
0. inventory服务更新缓存成功后，会发送"订单生成"消息。消息消费者接到后，会写数据库，此时订单的状态为"未确认"。
0. order服务当调用inventory服务成功后，会写自己的数据库。同时向MQ中发送订单确认消息。
0. inventory服务上的消费者接到订单确认消息时，把订单状态更新为"已确认"。

#### 异常恢复
* order服务调用inventory服务失败。此时并没有写数据库和缓存，无影响。
* order服务调用inventory服务后，更新缓存和发"订单生成"消息成功，但是返回order服务时失败。此时order服务会报错，inventory服务已扣库存，并产生一条"未确认"的订单。
为恢复这种不一致状态，需要额外的定时JOB扫描inventory的数据库，发现有订单长时间停在"未确认"状态且order服务中没有对应订单号的订单，就把订单置为"失败"并恢复库存。
就需要判断order服务数据库中相同订单号的订单状态，决定把订单置为"失败"并恢复库存，还是把订单变成"已确认"状态。（暂未实现）
* order服务调用inventory服务成功，自身写数据库失败。可以向MQ中发送"订单取消"消息。inventory服务接到"订单取消"消息后，恢复库存，取消订单。(暂未实现)
* order写数据成功，MQ消息发送失败。定时JOB扫描inventory的数据库，发现有订单长时间停在"未确认"状态且order服务中有对应订单号的订单，就把订单置为"已确认"。(暂未实现)

## 缓存

#### Cache-Aside

goods服务利用了spring的cache注解实现了Cache-Aside模式。在查询和新增goods对象时，会把结果放入缓存，加速查询。
实际情况下，新增goods对象时不应该放入缓存。因为缓存空间有限，只应该放比较热的数据。

#### Cache-As-SOR

inventory服务为了能快速判断库存是否充足并扣除库存，将所有商品的库存量都放入了redis缓存。
order服务调用时，只操作缓存。缓存更新成功后，通过RocketMQ发消息，异步写入数据库。

## 限流
使用redis实现了支持分布式限流的service。目前需要在controller中调用限流方法。未来可以改成配置方式，在filter中实现。


    public boolean canCreateOrder() {
        if(!redisTemplate.hasKey(CREATE_ORDER_TIME_KEY) || !redisTemplate.hasKey(CREATE_ORDER_COUNTER_KEY)) {
            redisTemplate.opsForValue().set(CREATE_ORDER_TIME_KEY, "0" , 1, TimeUnit.SECONDS);
            redisTemplate.opsForValue().set(CREATE_ORDER_COUNTER_KEY, "0" ,2, TimeUnit.SECONDS);
        }
        if(redisTemplate.hasKey(CREATE_ORDER_TIME_KEY)
                && redisTemplate.opsForValue().increment(CREATE_ORDER_COUNTER_KEY,1L) > CREATE_ORDER_LIMIT) {
            return false;
        }
        
        return true;
    }
    
## 熔断
现在的场景认为扣库存是必须，没法降级，熔断后业务失败。

`@FeignClient(serviceId = "inventory", fallback = InventoryFallbackServiceImpl.class)`

## 分库分表
order服务中，通过Sharing-JDBC实现了分表。

