package system;

import java.util.function.Consumer;
import datamodel.Order;
import datamodel.OrderItem;

final class OrderProcessor implements Components.OrderProcessor {

	public OrderProcessor(InventoryManager inventoryManager) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean accept(Order order) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean accept(Order order, Consumer<Order> acceptCode, Consumer<Order> rejectCode,
			Consumer<OrderItem> rejectedOrderItemCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long orderValue(Order order) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long vat(long grossValue) {
		return vat(grossValue, 1);
	}

	@Override
	public long vat(long grossValue, int rateIndex) {
		double rate = 0;
		switch (rateIndex) {
		case 1:
			rate = 1.19;
			break;
		case 2:
			rate = 1.07;
			break;
		}
		return Math.round(grossValue - grossValue / rate);
	}

}