package model;

/**
 * Created by Espo on 3/10/2018.
 */

public class OrderPojo {
    String order_id;
    String order_date;

    public OrderPojo(String order_id, String order_date) {
        this.order_id = order_id;
        this.order_date = order_date;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }


}
