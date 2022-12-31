class MyDate extends java.util.Date{

    public int myGetMonth(){

        return this.getMonth()+1;
    }
    public void mySetMonth(int month){
        if(month >12 || month<0) throw new IllegalArgumentException();
        else this.setMonth(month-1);
    }
    public int myGetYear(){
        return this.getYear()+1900;
    }
    public void mySetYear(int year){
        if(year <1950)throw new IllegalArgumentException();
        else this.setYear(year - 1900);
    }
    public String toMyString(){
        return this.getDate() +"-" +this.myGetMonth() +"-" + this.myGetYear();
    }
}
public class Medecine {

    private String name;
    private String id;
    private float price;
    private MyDate expDate;
    private int qty;
    private int unit;

    public Medecine(){
        expDate = new MyDate();
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public MyDate getExpDate() {
        return expDate;
    }

    public void setExpDate(MyDate expDate) {
        this.expDate = expDate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }
    @Override
    public String toString(){
        return this.name +", "+this.id + ", "+ this.price + ", "+ this.expDate.toMyString()+", "+this.qty +", "+ this.unit;
    }

}
