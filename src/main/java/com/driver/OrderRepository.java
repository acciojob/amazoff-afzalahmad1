package com.driver;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
@Component
public class OrderRepository {
    private HashMap<String,Order> orderMap;
    private HashMap<String,DeliveryPartner> deliveryPartnerMap;
    private  HashMap<String, List<String>> orderPartnerPair;
    private HashMap<String,Integer> orderCountMap;
    public OrderRepository(){
        this.orderMap = new HashMap<>();
        this.deliveryPartnerMap = new HashMap<>();
        this.orderPartnerPair = new HashMap<>();
        this.orderCountMap = new HashMap<>();
    }
    public void addOrder(Order order){
        orderMap.put(order.getId(),order);
    }
    public void addPartner(String partnerId){
        deliveryPartnerMap.put(partnerId,deliveryPartnerMap.get(partnerId));
        orderCountMap.put(partnerId,orderCountMap.getOrDefault(partnerId,0)+1);
    }

    public void addOrderPartnerPair(String orderId,String partnerId){
        if(orderMap.containsKey(orderId) && deliveryPartnerMap.containsKey(partnerId)){
            List<String> orderPartnerList = new ArrayList<>();
            if(orderPartnerPair.containsKey(partnerId)){
                orderPartnerList = orderPartnerPair.get(partnerId);
            }
            orderPartnerList.add(orderId);
            orderPartnerPair.put(partnerId,orderPartnerList);
        }
    }
    public Order getOrderById(String orderId){
        return orderMap.get(orderId);
    }

    public DeliveryPartner getPartnerById(String partnerId){
        return deliveryPartnerMap.get(partnerId);
    }

    public int ordersCount(String partnerId){
        return orderCountMap.get(partnerId);
    }

    public List<String> getOrdersByPartnerId(String partnerId){
        List<String> listOfOrder = new ArrayList<>();
        if(orderPartnerPair.containsKey(partnerId))
            listOfOrder = orderPartnerPair.get(partnerId);
        return listOfOrder;
    }

    public List<String> getAllOrders(){
        List<String> listOfOrders = new ArrayList<>();
        for(String orders : orderMap.keySet()){
            listOfOrders.add(orders);
        }
        return listOfOrders;
    }

    public int countOfUnassignedOrders(){
        int count=0;
        for(String order : orderMap.keySet()){
            if(orderMap.containsKey(order) && !orderPartnerPair.containsKey(order)){
                count++;
            }
        }
        return count;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        List<String> list = orderPartnerPair.get(partnerId);
        int time = Integer.MIN_VALUE;
        for(String orderId : list){
            Order order = orderMap.get(orderId);
            time = Math.max(time,order.getDeliveryTime());
        }
        int HH = time/60;
        int MM = time%60;
        return (HH +":"+MM);

    }

    public void deletePartnerById(String partnerId){
        orderPartnerPair.remove(partnerId);
        deliveryPartnerMap.remove(partnerId);
    }

    public void deleteOrderById(String orderId){
        orderMap.remove(orderId);
        orderPartnerPair.remove(orderId);
    }


}
