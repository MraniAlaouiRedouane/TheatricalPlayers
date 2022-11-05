import java.text.NumberFormat;
import java.util.*;

public class Invoice {

  public Customer customer;
  public List<Performance> performances;
  private float totalPrice;
  private int volumeCredits;
  private float fidelityDiscount;

  final NumberFormat frmt = NumberFormat.getCurrencyInstance(Locale.US);

  public Invoice(Customer customer, List<Performance> performances) {
    this.customer = customer;
    this.performances = performances;
  }

  private void calculateInvoice(HashMap<String, Play> plays) {
    this.volumeCredits = 0;
    this.totalPrice = 0;
    this.fidelityDiscount = 0;

    for (Performance perf : this.performances) {
      final Play play = plays.get(perf.playID);
      totalPrice += play.calculatePrice(perf.audience);
      volumeCredits += play.calculateCredits(perf.audience);
    }

    this.customer.fidelityBalance += volumeCredits;

    if (this.customer.fidelityBalance >= 150) {
      fidelityDiscount = 15;
      totalPrice -= fidelityDiscount;
      this.customer.fidelityBalance -= 150;
    }
  }

  public String printText(HashMap<String, Play> plays) {

    calculateInvoice(plays);

    StringBuffer result = new StringBuffer(String.format("Invoice for %s\n", this.customer.customerName));

    for (Performance perf : this.performances) {
      final Play play = plays.get(perf.playID);
      result.append (String.format("  %s: %s (%s seats)\n", play.name, frmt.format(play.calculatePrice(perf.audience)), perf.audience));
    }

    result.append (String.format("Total Amount: %s\n", frmt.format(this.totalPrice)));
    result.append (String.format("%s credits earned.\n", volumeCredits));

    if (this.fidelityDiscount > 0) {
      result.append (String.format("You earned a discount of %s due to your Fidelity Points!\n", frmt.format(this.fidelityDiscount)));
    }

    result.append (String.format("Your Fidelity Points Balance is %s", this.customer.fidelityBalance));
    return result.toString();

  }

  public String printHTML(HashMap<String, Play> plays) {

    calculateInvoice(plays);

    StringBuffer result = new StringBuffer(String.format("<!DOCTYPE html>\n"));
    result.append (String.format("<html>\n"));
    result.append (String.format("<head>\n"));
    result.append (String.format("<style>\n"));
    result.append (String.format("table, th, td {border: 1px solid black %C width: 500px %C text-align: center %C }\n",59,59,59));
    result.append (String.format("caption{padding-top: 10px; caption-side: bottom;}\n"));
    result.append (String.format("</style>\n"));
    result.append (String.format("</head>\n"));
    result.append (String.format("<body>\n"));
    result.append (String.format("<h1>Invoice</h1>\n" +
            "<p><b>Client: </b>BigCo</p>\n" +
            "<table>\n" +
            "<tr>\n" +
            "<th>Piece</th>\n" +
            "<th>Seats Sold</th>\n" +
            "<th>Price</th>\n" +
            "</tr>\n"));


    for (Performance perf : this.performances) {
      final Play play = plays.get(perf.playID);
      result.append (String.format("<tr>\n" +
              "<td>%s</td>\n" +
              "<td>%s</td>\n" +
              "<td>%s</td>\n" +
              "</tr>\n"
              , play.name, perf.audience, frmt.format(play.calculatePrice(perf.audience))));
    }

    result.append (String.format("<tr>\n" +
            "<th colspan=\"2\">Total Owed:</th>\n" +
            "<td>%s</td>\n" +
            "</tr>\n"
            ,frmt.format(this.totalPrice)));

    result.append (String.format("<tr>\n" +
            "<th colspan=\"2\">Fidelity Points Earned:</th>\n" +
            "<td>51</td>\n" +
            "</tr>\n", volumeCredits));

    if (this.fidelityDiscount > 0) {
      result.append (String.format("<tr>\n" +
              "<th colspan=\"2\">Fidelity Points Discount:</th>\n" +
              "<td>%s</td>\n" +
              "</tr>\n", frmt.format(this.fidelityDiscount)));
    }
    result.append (String.format("<tr>\n" +
            "<th colspan=\"2\">Remaining Fidelity Points:</th>\n" +
            "<td>%s</td>\n" +
            "</tr>\n", this.customer.fidelityBalance));

    result.append (String.format("<caption><i> Payement is required in under 30 days. Daily newsletters that you can't unsubscribe from will be e-mailed to you if you don't.</i></caption>\n"));
    result.append (String.format("</table>\n"));
    result.append (String.format("</body>\n"));
    result.append (String.format("</html>\n"));


    return result.toString();

  }
}