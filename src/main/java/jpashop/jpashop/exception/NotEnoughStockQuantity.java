package jpashop.jpashop.exception;

public class NotEnoughStockQuantity extends RuntimeException {

    public NotEnoughStockQuantity() {
      super();
    }

    public NotEnoughStockQuantity(String message) {
          super(message);
      }

    public NotEnoughStockQuantity(String message, Throwable cause) {
      super(message, cause);
    }

    public NotEnoughStockQuantity(Throwable cause) {
      super(cause);
    }

}
