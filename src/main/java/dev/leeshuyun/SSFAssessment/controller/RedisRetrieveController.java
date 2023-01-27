package dev.leeshuyun.SSFAssessment.controller;

@RestController
public class RedisRetrieveController {
    // returns response entities instead
    @GetMapping(path="{orderId}")
    public ResponseEntity<String> getOrder(@PathVariable String orderId) throws IOException{
        //use the services method to find the object by Id
        PizzaOrder pOrder = pizzaServices.findById(orderId);

        if (pOrder == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSON().toString());
    }
}
