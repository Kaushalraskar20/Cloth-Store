package POJO;

public class Cart implements java.io.Serializable {

    private Integer cartId;
    private Integer userId;
    private Integer productId;
    private Integer quantity;

    public Cart() {
    }

    public Cart(Integer userId,
                Integer productId,
                Integer quantity) {

        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}