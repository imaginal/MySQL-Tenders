/*
 * Author: Voldymyr Flonts
 * 3rd course 6th semester
 * Methods of developing a graphical interface
 */
package models;


public class Tender {

    private String tenderID;

    private String title;

    private String startDate;

    private String endDate;

    private String amount;

    private String currency;

    public String getID ()
    {
        return tenderID;
    }

    public void setID (String id)
    {
        this.tenderID = id;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    public String getStartDate ()
    {
        return startDate;
    }

    public void setStartDate (String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate ()
    {
        return endDate;
    }

    public void setEndDate (String endDate)
    {
        this.endDate = endDate;
    }

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getCurrency ()
    {
        return currency;
    }

    public void setCurrency (String currency)
    {
        this.currency = currency;
    }

    @Override
    public String toString()
    {
        return "Tender [id="+tenderID+" title="+title+" amount="+amount+"]";
    }
}