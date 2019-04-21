package loh.calvin.imanagead;

public class Product {
    public String pname, ptype, pquantity, pprice, pimage;

    public Product(String pname, String ptype, String pquantity, String pprice, String pimage) {
        this.pname = pname;
        this.ptype = ptype;
        this.pquantity = pquantity;
        this.pprice = pprice;
        this.pimage = pimage;
    }

    public Product() {
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPtype() {
        return ptype;
    }

    public void setPtype(String ptype) {
        this.ptype = ptype;
    }

    public String getPquantity() {
        return pquantity;
    }

    public void setPquantity(String pquantity) {
        this.pquantity = pquantity;
    }

    public String getPprice() {
        return pprice;
    }

    public void setPprice(String pprice) {
        this.pprice = pprice;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }
}
