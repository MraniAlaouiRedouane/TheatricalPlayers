public abstract class Play {

  public String name;
  public String type;
  public abstract float calculatePrice(int audience);
  public abstract int calculateCredits(int audience);
  public Play(){};

  public Play(String name, String type) {
    this.name = name;
    this.type = type;
  }
}
