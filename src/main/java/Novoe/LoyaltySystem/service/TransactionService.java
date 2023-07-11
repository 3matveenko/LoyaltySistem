package Novoe.LoyaltySystem.service;

import Novoe.LoyaltySystem.exception.BonusException;
import Novoe.LoyaltySystem.exception.ForbiddenException;
import Novoe.LoyaltySystem.model.CardItem;
import Novoe.LoyaltySystem.model.Company;
import Novoe.LoyaltySystem.model.Transaction;
import Novoe.LoyaltySystem.model.jackson.Item;
import Novoe.LoyaltySystem.model.jackson.Order;
import Novoe.LoyaltySystem.repository.TransactionRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
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

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CardItemService cardItemService;

    @Autowired
    CompanyService companyService;

    public String makeTransaktion(String jsonText, Company company) throws ArithmeticException, JsonProcessingException, BonusException {
        //пропускать неизв поля
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Order order = objectMapper.readValue(jsonText, Order.class);
        JsonNode jsonNode = objectMapper.readTree(jsonText);
        JsonNode jsonNode1 = jsonNode.get("bonus");
        double orderBonus = Double.parseDouble(jsonNode1.toString());
        if(order.getItems()!=null){
            List<Item> items = order.getItems();
        StringBuilder builder = new StringBuilder();
        for (Item item: items) {
            builder.append("lin.add(new A(" + item.getAmount() + ", " + item.getPrice() + ", \"" +item.getName() + "\", " + item.getCount() + "));");
        }
        String everyone = builder.toString();
        long carditemId = order.getCard_id();
        CardItem cardItem = cardItemService.getCardItemById(carditemId).get();
        int countOrder = transactionRepository.findAllByCardItem(cardItem).size();
        double bonusAmount = 0;
        double moneyAmount = 0;
        String formula;
        if(orderBonus==0){
            bonusAmount = 0;
            formula = cardItem.getCard().getTypeOfDiscount();
        } else if (orderBonus > 0) {
            bonusAmount = orderBonus;
            formula = "for (A l: lin) {l.amount = l.amount - (b.bonusAmount/lin.size());l.price = l.amount/l.count;} b.bonusAmount = b.bonusAmount*-1;";
        } else {
            throw new BonusException("bonus exception");
        }
        String line1 = """
                import java.util.ArrayList;
                import java.util.List;
                //l.name - имя товара в чеке
                //l.count - количество шт товара
                //l.price - цена за шт
                //l.amount - сумма строки в тг
                //b.bonusAmount - бонусы
                //b.moneyAmount - сумма всего чека
                //b.countOrder - порядковый номер транзакции
                
                class A {public double amount; public double price; public String name; public double count; A(double amount, double price, String name, double count) { this.amount = amount; this.price=price; this.name=name;this.count=count;} }
                class B {public double bonusAmount;public double moneyAmount;public double countOrder;B (double bonusAmount, double moneyAmount, double countOrder){this.bonusAmount = bonusAmount;this.moneyAmount = moneyAmount;this.countOrder = countOrder;}}
                List<A> lin = new ArrayList();
                  """
                + "\nB b = new B("+bonusAmount+","+moneyAmount+","+countOrder+");\n" +
                everyone+"\n"+
                """
                System.out.println("line1");
                System.out.println("line2");
                //код обработки \n
                """
                + formula +
                """
                //код обработки
                String json = "";
                for (A l: lin) {json += "{\\"amount\\":"+(l.price * l.count)+",\\"price\\":"+l.price+",\\"name\\":\\""+l.name+"\\",\\"count\\":"+l.count+"},";b.moneyAmount  += l.amount;}                    
                String result = "[" + json.substring(0, json.length()-1) + "]";
                String bonusAmount = Double.toString(b.bonusAmount);
                String moneyAmount = Double.toString(b.moneyAmount);
                String countOrder = Double.toString(b.countOrder); 
                System.out.println("line3");                
                """;
            System.out.println(line1);
        String resp = "";
        JShell jShell = JShell.create();
        String[] lines = line1.split("\r?\n|\r");
        for (String line : lines) {
            jShell.eval(line);
        }
        List<Snippet> ls = jShell.snippets().toList();
        for (Snippet sn : ls) {
            if(jShell.status(sn) != Snippet.Status.VALID){
                throw new ArithmeticException();
            }
            if (sn instanceof VarSnippet) {
                PersistentSnippet ps = (PersistentSnippet) sn;
                VarSnippet varSnippet = (VarSnippet) sn;

                switch (ps.name()) {
                    case "result":
                        resp = StringEscapeUtils.unescapeJava(jShell.varValue(varSnippet));
                        break;
                    case "bonusAmount":
                        String stringBonusAmount = jShell.varValue(varSnippet).replace("\"", "");
                        bonusAmount = Double.parseDouble(stringBonusAmount);
                        break;
                    case "moneyAmount":
                        String stringMoneyAmount = jShell.varValue(varSnippet).replace("\"", "");
                        moneyAmount = Double.parseDouble(stringMoneyAmount);
                        break;
                }
            }
        }
        resp = resp.substring(1, resp.length()-1);
        if (resp.length() > 0) {
            Transaction transaction = new Transaction();
            transaction.setDate(new Date());
            transaction.setCompany(company);
            transaction.setCardItem(cardItem);
            transaction.setOrderIn(jsonText);
            transaction.setMoneyAmount(moneyAmount);
            transaction.setBonusAmount(bonusAmount);
            transaction.setOrderOut(resp);
            transactionRepository.save(transaction);
        }
            return resp;
        } else {
           return "[]";
        }
    }
}
