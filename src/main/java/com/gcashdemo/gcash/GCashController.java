package com.gcashdemo.gcash;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GCashController {
    @RequestMapping
    public String helloWorld(){
        return "Hello World from Spring Boot";
    }

    @RequestMapping("/goodbye")
    public String goodbye(){
        return "Goodbye from Spring Boot";
    }

    @RequestMapping("/compute/{flatrate}/{weight}/{height}/{width}/{length}")
    public String compute(@PathVariable double flatrate, @PathVariable double weight, @PathVariable double height, @PathVariable double width, @PathVariable double length) throws JSONException {

        double fee = 0;
        flatrate = 50;
        double volume = height*width*length;
        double cbm = volume/5000;
        double cost = 0;
        String message = volume + " , " + weight;

        if(weight>50 || cbm >50){
            message = "Weight exceeds 50kg";
        }
        else if( (weight >10 && weight<=50 ) || (cbm>10 && cbm <50)){
            cost = 20 * weight;
            message = "Delivery fee: "+ cost;
        }
        else {
             if (volume < 1500) {
                cost = (0.03 * volume) + flatrate;
                message = "Delivery fee: " + cost;
            } else if (volume >= 1500 && volume < 2500) {
                cost = (.04 * volume) + flatrate;
                message = "Delivery fee: " + cost;
            } else if (volume >= 2500) {
                cost = (.05 * volume) + flatrate;
                message = "Delivery fee: " + cost;
            } else {
                cost = flatrate;
                message = "Delivery fee: " + cost;
            }
        }

        String uri = "https://mynt-exam.mocklab.io/voucher/MYNT?key=apikey";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

       JSONObject obj = new JSONObject(result);
       String discount = obj.getString("discount");
       Double finalFee = cost - Double.parseDouble(discount);
        return message + "\n Voucher -"+ discount + "\n Final Amount: " +( Math.abs(finalFee) );
    }
}
