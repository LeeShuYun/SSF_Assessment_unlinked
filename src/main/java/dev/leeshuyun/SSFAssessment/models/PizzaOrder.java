package dev.leeshuyun.SSFAssessment.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Digits;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;


public class PizzaOrder implements Serializable{
    //Validation rules

    //we can't use @Size(min=7, message="") for integer types. throws compiler error. String
    //we could use @Pattern regex, because phone number country code +65 cannot be int. or @Length of the string. 
    //we could use Digit?? for phone number
    //@Min(value=7, message="Phone number must be at least 7 digit") //use for integer

    //this pizza contains only ONE type of pizza with its size of small medium or large, and quantity 
  // instance variables =====================================
    //https://stackoverflow.com/questions/285793/what-is-a-serialversionuid-and-why-should-i-use-it
    //declare serialVersionUID
    //used during deserialization to verify that the sender and receiver of a serialized object have loaded classes for that object that are compatible
    private static final long serialVersionUID = 1L;

    private String orderId; //8 digit hexadecimal string
    
    //0 = sm, 1 = md, 2 = lg
    // @NotEmpty(message="Please choose the size of pizza.")
    @Min(value=1, message="Please choose the size of pizza.")
    private String pizzaSize;
    
    //for the bella, margherita, marinara, 5 types of pizza
    @NotEmpty(message="Please choose the type of pizza.")
    private String selectedPizza;

    @Min(value=1, message="Minimum quantity is 1")
    @Max(value=10, message="Maximum quantity is 10.")
    // @Digits(integer=5, fraction=2, message="x quantity is 5 digits and 2 decimals")
    private int quantity;
    
    @NotEmpty(message="Please add an address.")
    private String address;

    @NotEmpty(message="Please add an address.")
    private String phone;

    private Float pizzaCost;

    private boolean isRushOrder;

    private Float totalOrderCost;

    private String comments;
    //methods ================================
    
    //generates 8 digits hexadecimal string. or any digits, but we only use for 8 here
    //"synchronized" makes sure that the id is unique, so we don't have duplicate ids being passed out
    //basically this method is NON async. it makes sure there's a unique id.
    private synchronized String generateId(int numChars) {
        Random r = new Random();
        //Stringbuilder lets us manipulate a string like it was an array
        StringBuilder sb = new StringBuilder();

        //while we don't have 8 digits, keep generating hexString
        while (sb.length() < numChars) {
            //r.nextInt() is a generator that spits 1 random number everytime it's called
            //.toHexString() converts it to hex
		    //sb.append() adds to the end of the string
            sb.append(Integer.toHexString(r.nextInt()));
        }
        //if the hexadecimal is too long, we slice/substring it down to numChar length
        return sb.toString().substring(0, numChars);
    }

    //creates JsonObjectbuilder from string
    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("orderId", this.getOrderId())
                .add("name", this.getName())
                .add("phone", this.getPhone())
                .add("rush", this.isRushOrder())
                .add("comments", this.getComments())
                .add("pizza", this.getSelectedPizza())
                .add("size", this.getPizzaSize())
                .add("quantity", this.getQuantity())
                .add("total", this.getTotalOrderCost());
    }

    //creates PizzaOrder obj from json object
    public static PizzaOrder createJson(JsonObject o) {
        PizzaOrder t = new PizzaOrder();
        JsonNumber count = o.getJsonNumber("count");
        String type = o.getString("type");
        t.count = count.intValue();
        t.type = type;
        return t;
    }
    //getter and setters ======================================
    public String getOrderId(){
        return orderId;
    }
    
    public void setOrderId(String orderId){
        this.orderId = orderId;
    }
    public String getPhone(){
        return phone;
    };
    public void setPhone(String phone){
        this.phone = phone;
    };
    public String getPizzaSize(){
        return pizzaSize;
    };

    public void setPizzaSize(String pizzaSize){
        this.pizzaSize = pizzaSize;
    }

    public String getSelectedPizza(){
        return selectedPizza;
    };
    public void setSelectedPizza(String selectedPizza){
        this.selectedPizza = selectedPizza;
    }

    public int getQuantity(){
        return quantity;
    };
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getPizzaCost() {
        return pizzaCost;
    }

    public void setPizzaCost(Float pizzaCost) {
        this.pizzaCost = pizzaCost;
    }

    public boolean isRushOrder() {
        return isRushOrder;
    }

    public void setRushOrder(boolean isRushOrder) {
        this.isRushOrder = isRushOrder;
    }

    public Float getTotalOrderCost() {
        return totalOrderCost;
    }

    public void setTotalOrderCost(Float totalOrderCost) {
        this.totalOrderCost = totalOrderCost;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
