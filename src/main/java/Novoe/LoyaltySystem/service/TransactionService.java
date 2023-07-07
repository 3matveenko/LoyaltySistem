package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.model.CardItem;
import Novoe.LoyaltySystem.model.Transaction;
import Novoe.LoyaltySystem.model.jackson.Item;
import Novoe.LoyaltySystem.model.jackson.Order;
import Novoe.LoyaltySystem.repository.TransactionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jdk.jshell.JShell;
import jdk.jshell.PersistentSnippet;
import jdk.jshell.Snippet;
import jdk.jshell.VarSnippet;
import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardItemService cardItemService;


    public String makeTransaktion(String jsonText) throws Exception {


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
                        
                        String json = "";
                        for (A l: lin) {json += "{\\"amount\\":"+l.amount+",\\"price\\":"+l.price+",\\"name\\":\\""+l.name+"\\",\\"count\\":"+l.count+"},";}                    
                        String result = "[" + json.substring(0, json.length()-1) + "]";
                                            
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
        Transaction transaction = new Transaction();
        Date date = new Date();
        transaction.setDate(date);
        transaction.setCompany()
        return resp;
    }
}
