package loh.calvin.imanagead;

public class Product {
    public String pname, ptype, pquantity, pprice, pimage, pdate, pcurrency;

    public Product(String pname, String ptype, String pquantity, String pprice, String pimage, String pdate, String pcurrency) {
        this.pname = pname;
        this.ptype = ptype;
        this.pquantity = pquantity;
        this.pprice = pprice;
        this.pimage = pimage;
        this.pdate = pdate;
        this.pcurrency = pcurrency;
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

    public String getPdate() {
        return pdate;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public String getPcurrency() {
        return pcurrency;
    }

    public void setPcurrency(String pcurrency) {
        this.pcurrency = pcurrency;
    }
}
