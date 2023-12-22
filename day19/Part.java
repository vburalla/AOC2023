package day19;

public class Part {
    private Integer x;
    private Integer m;
    private Integer a;
    private Integer s;

    public Part(String partData) {
        var data = partData.replace("{","").replace("}","").split(",");
        this.x = Integer.parseInt(data[0].split("=")[1]);
        this.m = Integer.parseInt(data[1].split("=")[1]);
        this.a = Integer.parseInt(data[2].split("=")[1]);
        this.s = Integer.parseInt(data[3].split("=")[1]);
    }

    public Integer getCategory(String category) {
        return switch(category) {
            case "x" -> this.x;
            case "m" -> this.m;
            case "a" -> this.a;
            case "s" -> this.s;
            default -> throw new RuntimeException("Not available category");
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("(");
        if(this.x != null)
            sb.append(this.x).append(", ");
        if(this.m !=null)
            sb.append(this.m).append(", ");
        if(this.a != null)
            sb.append(this.a).append(", ");
        if(this.s != null)
            sb.append(this.s);
        sb.append(")");
        return sb.toString();
    }

    public Integer sumCategoriesRating() {
        return this.x + this.m + this.a + this.s;
    }
}
