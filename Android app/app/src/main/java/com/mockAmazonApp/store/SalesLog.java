package com.mockAmazonApp.store;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class keeps logs of different types of sales.
 *
 */
public class SalesLog implements Serializable {

  ArrayList<Sale> allSales = new ArrayList<Sale>();

  /**
   * This constructor creates a log of sales.
   * 
   * @param allSales
   */
  public SalesLog(ArrayList<Sale> allSales) {
    this.allSales = allSales;
  }

  /**
   * Returns a list of all sales.
   * 
   * @return allSales
   */
  public ArrayList<Sale> getallSales() {
    return allSales;
  }
}

