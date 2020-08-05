package com.test;

import java.util.Random;

public class Order {

	public Integer orderId = new Random().nextInt();
	public String orderName = Integer.toString(new Random().nextInt());
	public String orderAddress;
	public String orderStatus;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderName=" + orderName + ", orderAddress=" + orderAddress
				+ ", orderStatus=" + orderStatus + "]";
	}

}
