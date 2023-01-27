package dev.leeshuyun.SSFAssessment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.leeshuyun.SSFAssessment.models.PizzaOrder;
import dev.leeshuyun.SSFAssessment.service.PizzaService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PizzaService {
    // private static final Logger logger = LoggerFactory.getLogger(PizzaService.class);

    //for saving into redis
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    //attach the pizza total cost with and without rush to the pizza obj with and without rush
    public PizzaOrder calcOrderCost(PizzaOrder pOrder){
        String pizzaType = pOrder.getSelectedPizza();
        float pizzaCost = 0f;
        if (pizzaType.equals("bella") || pizzaType.equals("marinara") || pizzaType.equals("spianatacalabrese")){
            pizzaCost = 30f * pOrder.getQuantity() * multiplier(pOrder);
        }else if (pizzaType.equals("margherita")) {
            pizzaCost = 22f * pOrder.getQuantity() *  multiplier(pOrder);
        }else if (pizzaType.equals("trioformaggio")) {
            pizzaCost = 25f * pOrder.getQuantity() *  multiplier(pOrder);
        }

        pOrder.setPizzaCost(pizzaCost);

        if (pOrder.isRushOrder()){
            pOrder.setTotalOrderCost(pizzaCost + 2.0f);
        }else{
            pOrder.setTotalOrderCost(pizzaCost);
        }
        return pOrder;
    }
    private float multiplier(PizzaOrder pOrder){
        if (pOrder.getPizzaSize().equals("sm")){
            return 1f;
        }else if(pOrder.getPizzaSize().equals("md")){
            return 1.2f;
        }else{
            return 1.5f;
        }
    }

    //saves the delivery order to redis
    public void save(final PizzaOrder pizzaOrder){
        //sets the id as key and mastermindObj as String value
        redisTemplate.opsForValue().set(pizzaOrder.getOrderId(), pizzaOrder.toJSON().toString());

        //test by retrieving the Value data we just saved under the ID, getting from redis 
        String result = (String) redisTemplate.opsForValue().get(pizzaOrder.getOrderId());
        
        //if we successfully save the game, we return 1
        if (result != null)
            return 1;
        return 0;
    }

    public 


}
