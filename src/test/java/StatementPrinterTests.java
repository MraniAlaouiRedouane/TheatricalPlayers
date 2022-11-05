import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.approvaltests.Approvals.verify;

public class StatementPrinterTests {

    @Test
    void exampleStatement() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  new TragedyInfo("Hamlet"));
        plays.put("as-like",  new ComedyInfo("As You Like It"));
        plays.put("othello",  new TragedyInfo("Othello"));
        Invoice invoice = new Invoice(new Customer("1", "BigCo", 103), List.of(
                new Performance("hamlet", 55),
                new Performance("hamlet", 30),
                new Performance("as-like", 20),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        var result = invoice.printText(plays);

        verify(result);
    }

    @Test
    void exampleStatementNoDiscount() {
     HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  new TragedyInfo("Hamlet"));
        plays.put("as-like",  new ComedyInfo("As You Like It"));
        plays.put("othello",  new TragedyInfo("Othello"));
        Invoice invoice = new Invoice(new Customer("1", "BigCo", 0), List.of(
                new Performance("hamlet", 55),
                new Performance("hamlet", 30),
                new Performance("as-like", 20),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        var result = invoice.printText(plays);

        verify(result);
    }

    @Test
    void exampleStatementHTML() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  new TragedyInfo("Hamlet"));
        plays.put("as-like",  new ComedyInfo("As You Like It"));
        plays.put("othello",  new TragedyInfo("Othello"));
        Invoice invoice = new Invoice(new Customer("1", "BigCo", 103), List.of(
                new Performance("hamlet", 55),
                new Performance("hamlet", 30),
                new Performance("as-like", 20),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        var result = invoice.printHTML(plays);

        verify(result);
    }

    @Test
    void exampleStatementNoDiscountHTML() {
        HashMap<String, Play> plays = new HashMap<>();
        plays.put("hamlet",  new TragedyInfo("Hamlet"));
        plays.put("as-like",  new ComedyInfo("As You Like It"));
        plays.put("othello",  new TragedyInfo("Othello"));
        Invoice invoice = new Invoice(new Customer("1", "BigCo", 0), List.of(
                new Performance("hamlet", 55),
                new Performance("hamlet", 30),
                new Performance("as-like", 20),
                new Performance("as-like", 35),
                new Performance("othello", 40)));

        var result = invoice.printHTML(plays);

        verify(result);
    }
}
