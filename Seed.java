
 public enum Seed {
    CROSS("X"),NOUGHT("O"),NO_SEED(" ");

    private String icon;
    
    private Seed(String icon){
        this.icon = icon;
    }

    public String getIcon(){
        return icon;
    }
 }
