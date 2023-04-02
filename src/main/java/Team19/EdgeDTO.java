package Team19;

public class EdgeDTO extends EdgeADT<String,Double> {

    public EdgeDTO(String from, String to, Double weight) {
        super(from, to, weight);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof EdgeDTO)) return false;
        EdgeDTO e = (EdgeDTO) obj;
        return ((e.from.equals(this.from) && e.to.equals(this.to))
                || (e.from.equals(this.to) && e.to.equals(this.from)))
                && e.weight == this.weight;
    }
}
