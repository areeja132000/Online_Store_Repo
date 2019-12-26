package com.b07.store;

import java.math.BigDecimal;
import java.util.HashMap;
import com.b07.inventory.Item;
import com.b07.users.User;

public interface Sale {

  /**
   * Return the sales Id.
   * 
   * @return id
   */
  public int getId();

  /**
   * Set the sales Id.
   * 
   * @param id
   */
  public void setId(int id);

  /**
   * Return the user connected to the sale.
   * 
   * @return user
   */
  public User getUser();

  /**
   * Set the user connected to the sale.
   * 
   * @param user
   */
  public void setUser(User user);

  /**
   * Return the total price of the sale.
   * 
   * @return totalPrice
   */
  public BigDecimal getTotalPrice();

  /**
   * Set the total price of the sale.
   * 
   * @param totalPrice
   */
  public void setTotalPrice(BigDecimal price);

  /**
   * Return map of items to quantity.
   * 
   * @return itemMap
   */
  public HashMap<Item, Integer> getItemMap();

  /**
   * Set map of items to quantity.
   * 
   * @param itemMap
   */
  public void setItemMap(HashMap<Item, Integer> itemMap);

}
