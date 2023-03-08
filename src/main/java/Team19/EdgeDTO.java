package Team19;

public class EdgeDTO {
    public String from;
    public String to;
    public int weight;

    public EdgeDTO(String from, String to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
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
