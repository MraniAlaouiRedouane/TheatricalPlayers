
public class ComedyInfo extends Play {

	public ComedyInfo(String name) {
		this.name = name;
	}

	public float calculatePrice(int audience) {
		return 300 + 3 * audience + (audience > 20 ? 100 + 5 * (audience - 20) : 0);
	}

	public int calculateCredits(int audience) {
		return Math.max(audience - 30, 0) + (int) Math.floor(audience / 5);
	}
}
