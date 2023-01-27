package dev.leeshuyun.SSFAssessment.controller;

@RestController
public class RedisRetrieveController {
    // returns response entities instead
    @GetMapping(path="${orderId}")
    public ResponseEntity<String> getOrder(@PathVariable String orderId){
        
    }
}
