package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.jackson.Item;
import Novoe.LoyaltySystem.model.jackson.Order;
import Novoe.LoyaltySystem.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.JShell;
import jdk.jshell.PersistentSnippet;
import jdk.jshell.Snippet;
import jdk.jshell.VarSnippet;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;


    public JSONObject makeTransaktion(String jsonText) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        Order order = objectMapper.readValue(jsonText, Order.class);
        List<Item> items = order.getItems();
        String everyone = new String();
        for (int i = 0; i < items.size(); i++) {
            everyone += "lin.add(new A(" + items.get(i).getAmount() + ", " + items.get(i).getPrice() + ", \"" +items.get(i).getName() + "\", " + items.get(i).getCount() + "));";
        }

        String line1 = """
                 import java.util.ArrayList;
                 import java.util.List;

                 class A {public int amount; public double price; public String name; public int count; A(int amount, double price, String name, int count) { this.amount = amount; this.price=price; this.name=name;this.count=count;} }
                 class B {public int bonus;  public int var; B (int bonus, int var){this.bonus = bonus;this.var = var;}}
                 
                 B b = new B(1000, 0);
                List<A> lin = new ArrayList();
                """
                +"\n"+everyone+
                """
                        \n
                        //код обработки
                             for (A l: lin) {l.price=l.price-10;} 
                        //код обработки
                        
                        String json = "{\\"items\\":[";
                        for (int i = 0; i < 10; i++) {json += "{\\"amount\\":\\""+lin.get(i).amount+"\\",\\"price\\":\\""+lin.get(i).price+"\\",\\"name\\":\\""+lin.get(i).name+"\\",\\"count\\":\\""+lin.get(i).count+"\\"},";}
                        int lastCommaIndex = json.lastIndexOf(",");
                        if (lastCommaIndex != -1) {
                            json = json.substring(0, lastCommaIndex) + "]}";
                        }
                        
                        String result = json;
                                            
                        """;
        String resp = "";
        JShell jShell = JShell.create();

        String[] lines = line1.split("\r?\n|\r");

        for (String line : lines) {
            jShell.eval(line);
        }

        List<Snippet> ls = jShell.snippets().toList();

        for (Snippet sn : ls) {
            if (sn instanceof VarSnippet) {
                PersistentSnippet ps = (PersistentSnippet) sn;
                if (Objects.equals(ps.name(), "result")) {
                    VarSnippet vs = (VarSnippet) sn;
                    resp = StringEscapeUtils.unescapeJava(jShell.varValue(vs));
                    System.out.println(jShell.varValue(vs));
                }
            }
        }
        ObjectMapper objectMapper1 = new ObjectMapper();
        Item order1 = objectMapper.readValue(resp, Item.class);

        JSONObject jsonObject = new JSONObject(jsonText);
        jsonObject.put("items", resp);
        System.out.println("jo = "+jsonObject);
        return jsonObject;
    }
}

//    public String makeTransaktion(String jsonText) throws Exception {
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonText);
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            String A = "";
//            A = "class A";
//            A = "List<A> list = new List<A>;";
//            for (int i = 0; i < itemsArray.length(); i++) {
//                A = "list.add(new A(10, 10, 10 ,10))";
//            }
//
//            User code
//
//            Result
//
//            }
//            for (int i = 0; i < itemsArray.length(); i++) {
//
//
//                JSONObject item = itemsArray.getJSONObject(i);
//                Integer amount = item.getInt("amount");
//
//                String line1 = """
//                 class A {public int amount; public int price; public String name; public int count; A(int amount, int price, String name, int count) { this.amount = amount; this.price=price; this.name=name;this.count=count;} }
//                 class B {public int bonus;  public int var; B (int bonus, int var){this.bonus = bonus;this.var = var;}}
//                 B b = new B(1000, 0);
//                A a1 = new A(100, 300, "шаровая", 3);
//                if(a1.name.equals("шаровая")){b.bonus = b.bonus + 30;}
//                for(int i = 0; i<10; i++){a1.amount= a1.amount*2;}
//                int result = b.bonus;
//                """;
//
//                JShell jShell = JShell.create();
//
//                String[] lines = line1.split("\r?\n|\r");
//
//                for (String line : lines) {
//                    jShell.eval(line);
//                }
//
//                List<Snippet> ls = jShell.snippets().toList();
//
//                for (Snippet sn : ls) {
//                    if (sn instanceof VarSnippet) {
//                        PersistentSnippet ps = (PersistentSnippet) sn;
//                        if (Objects.equals(ps.name(), "result")) {
//                            VarSnippet vs = (VarSnippet) sn;
//                            System.out.println(jShell.varValue(vs));
//                        }
//                    }
//                }
//
//                //List<SnippetEvent> a  = ;
//                //System.out.println("размер листа = "+a.size());
//                //for (SnippetEvent aa: a) {
//                //    System.out.println("зашел");
//                //    System.out.println(aa.value());
//                //item.put("amount", Double.parseDouble(aa.value()));
//                //}
//                //int newAmount = (int) (amount * 0.9);
//
//                //}
//                return jsonObject.toString();
//            }
//        } catch (Exception e) {
//
//            throw new Exception(e);
//        }
//        return "a";
//    }
//}


        //eval просто считает
//    public String makeTransaktion(String jsonText) throws Exception {
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonText);
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            for (int i = 0; i < itemsArray.length(); i++) {
//                JSONObject item = itemsArray.getJSONObject(i);
//                Integer amount = item.getInt("amount");
//                String line = amount.toString()+"*0.9";
//                JShell jShell = JShell.create();
//                List<SnippetEvent> a  = jShell.eval(line);
//                for (SnippetEvent aa: a) {
//                    System.out.println(aa.value());
//                    item.put("amount", Double.parseDouble(aa.value()));
//                }
//                int newAmount = (int) (amount * 0.9);
//
//            }
//            return jsonObject.toString();
//        } catch (Exception e) {
//
//            throw new Exception(e);
//        }
//    }
        //в ручную
//    public String makeTransaktion(String jsonText) throws Exception {
//
//        try {
//            JSONObject jsonObject = new JSONObject(jsonText);
//            JSONArray itemsArray = jsonObject.getJSONArray("items");
//
//            for (int i = 0; i < itemsArray.length(); i++) {
//                JSONObject item = itemsArray.getJSONObject(i);
//                int amount = item.getInt("amount");
//                int newAmount = (int) (amount * 0.9);
//                item.put("amount", newAmount);
//            }
//            return jsonObject.toString();
//        } catch (Exception e) {
//
//            throw new Exception(e);
//        }
//    }
