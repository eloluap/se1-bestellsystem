package system;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import datamodel.Customer;
import datamodel.Order;
import datamodel.OrderItem;

final class OutputProcessor implements Components.OutputProcessor {

	private final int printLineWidth = 95;
	
	public OutputProcessor(InventoryManager inventoryManager, OrderProcessor orderProcessor) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void printOrders(List<Order> orders, boolean printVAT) {
		StringBuffer sbAllOrders = new StringBuffer( "-------------" );
		StringBuffer sbLineItem = new StringBuffer();
		long gesamtPreis = 0;

		/*
		 * Insert code to print orders with all order items:
		 */
		for (Order o: orders) {
			String text = "#";
			long bestellPreis = 0;
			text += o.getId();
			text += ", ";
			Customer customer = o.getCustomer();
			text += splitName(customer, customer.getFirstName() + " " + customer.getLastName());
			text += "'s Bestellung: ";
			Iterator<OrderItem> i = o.getItems().iterator();
			while (i.hasNext()) {
				OrderItem item = i.next();
				text += item.getUnitsOrdered();
				text += "x ";
				text += item.getDescription();
				if (i.hasNext()) {
					text += ", ";
				}
				bestellPreis += item.getArticle().getUnitPrice()*item.getUnitsOrdered();
			}
			gesamtPreis += bestellPreis;
			String fmtBestellPreis = fmtPrice( bestellPreis, "EUR", 14 );
			sbLineItem = fmtLine( text, fmtBestellPreis, printLineWidth );
			sbAllOrders.append( "\n" );
			sbAllOrders.append( sbLineItem );
		}
		String fmtGesamtPreis = fmtPrice( gesamtPreis, "EUR", 14 );
		sbAllOrders.append( "\n" )
		.append( fmtLine( "-------------", "-------------", printLineWidth ) )
		.append( "\n" )
		.append( fmtLine( "Gesamtwert aller Bestellungen:", fmtGesamtPreis, printLineWidth ) );

		/*
		 * // convert price (long: 2345 in cent) to String of length 14, right-aligned
		 * -> "     23,45 EUR" String fmtPrice1 = fmtPrice( 2345, "EUR", 14 );
		 * 
		 * // format line item with left-aligned part1 and right-aligned-part2 of total
		 * length printLineWidth // and append to sbLineItem StringBuffer sbLineItem =
		 * fmtLine( "Erste Bestellung: einzelne Bestellpositionen (orderItems)",
		 * fmtPrice1, printLineWidth );
		 * 
		 * // append sbLineItem to sbAllOrders StringBuffer where all orders are
		 * collected sbAllOrders.append( "\n" ); sbAllOrders.append( sbLineItem );
		 * 
		 * // format another line for second order String fmtPrice2 = fmtPrice( 67890,
		 * "EUR", 14 ); sbLineItem = fmtLine(
		 * "Zweite Bestellung: einzelne Bestellpositionen (orderItems)", fmtPrice2,
		 * printLineWidth );
		 * 
		 * sbAllOrders.append( "\n" ); sbAllOrders.append( sbLineItem );
		 * 
		 * // calculate total price String fmtPriceTotal = pad( fmtPrice( 70235, "",
		 * " EUR" ), 14, true );
		 * 
		 * // append final line with totals sbAllOrders.append( "\n" ) .append( fmtLine(
		 * "-------------", "------------- -------------", printLineWidth ) ) .append(
		 * "\n" ) .append( fmtLine( "Gesamtwert aller Bestellungen:", fmtPriceTotal,
		 * printLineWidth ) );
		 */

		// print sbAllOrders StringBuffer with all output to System.out
		System.out.println( sbAllOrders.toString() );
	}

	@Override
	public void printInventory() {
		// TODO Auto-generated method stub

	}

	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter.
	 * Append currency. For example, 299, "EUR" -> "2,99 EUR"
	 * 
	 * @param price price as long in 1/100 (cents)
	 * @param currency currency as String, e.g. "EUR"
	 * @return price as String with currency and padded to minimum width
	 */
	@Override
	public String fmtPrice(long price, String currency) {
		String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
		return fmtPrice;
	}

	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter, add
	 * currency and pad to minimum width right-aligned.
	 * For example, 299, "EUR", 12 -> "    2,99 EUR"
	 * 
	 * @param price price as long in 1/100 (cents)
	 * @param currency currency as String, e.g. "EUR"
	 * @param width minimum width to which result is padded
	 * @return price as String with currency and padded to minimum width
	 */
	@Override
	public String fmtPrice(long price, String currency, int width) {
		String fmtPrice = pad( fmtPrice( price, "", " " + currency ), 14, true );
		return fmtPrice;
	}

	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter and
	 * prepend prefix and append postfix String.
	 * For example, 299, "(", ")" -> "(2,99)"
	 * 
	 * @param price price as long in 1/100 (cents)
	 * @param prefix String to prepend before price
	 * @param postfix String to append after price
	 * @return price as String
	 */
	@Override
	public String fmtPrice( long price, String prefix, String postfix ) {
		StringBuffer fmtPriceSB = new StringBuffer();
		if( prefix != null ) {
			fmtPriceSB.append( prefix );
		}

		fmtPriceSB = fmtPrice( fmtPriceSB, price );

		if( postfix != null ) {
			fmtPriceSB.append( postfix );
		}
		return fmtPriceSB.toString();
	}


	/**
	 * Format long-price in 1/100 (cents) to String using DecimalFormatter.
	 * For example, 299 -> "2,99"
	 * 
	 * @param sb StringBuffer to which price is added
	 * @param price price as long in 1/100 (cents)
	 * @return StringBuffer with added price
	 */
	@Override
	public StringBuffer fmtPrice( StringBuffer sb, long price ) {
		if( sb == null ) {
			sb = new StringBuffer();
		}
		double dblPrice = ( (double)price ) / 100.0;			// convert cent to Euro
		DecimalFormat df = new DecimalFormat( "#,##0.00",
			new DecimalFormatSymbols( new Locale( "de" ) ) );	// rounds double to 2 digits

		String fmtPrice = df.format( dblPrice );				// convert result to String in DecimalFormat
		sb.append( fmtPrice );
		return sb;
	}


	/**
	 * Pad string to minimum width, either right-aligned or left-aligned
	 * 
	 * @param str String to pad
	 * @param width minimum width to which result is padded
	 * @param rightAligned flag to chose left- or right-alignment
	 * @return padded String
	 */
	@Override
	public String pad( String str, int width, boolean rightAligned ) {
		String fmtter = ( rightAligned? "%" : "%-" ) + width + "s";
		String padded = String.format( fmtter, str );
		return padded;
	}
	
	/**
	 * Format line to a left-aligned part followed by a right-aligned part padded to
	 * a minimum width.
	 * For example:
	 * 
	 * <left-aligned part>                          <>       <right-aligned part>
	 * 
	 * "#5234968294, Eric's Bestellung: 1x Kanne         20,00 EUR   (3,19 MwSt)"
	 * 
	 * |<-------------------------------<width>--------------------------------->|
	 * 
	 * @param leftStr left-aligned String
	 * @param rightStr right-aligned String
	 * @param totalWidth minimum width to which result is padded
	 * @return String with left- and right-aligned parts padded to minimum width
	 */
	@Override
	public StringBuffer fmtLine(String leftStr, String rightStr, int totalWidth) {
		StringBuffer sb = new StringBuffer( leftStr );
		int shiftable = 0;		// leading spaces before first digit
		for( int i=1; rightStr.charAt( i ) == ' ' && i < rightStr.length(); i++ ) {
			shiftable++;
		}
		final int tab1 = totalWidth - rightStr.length() + 1;	// - ( seperator? 3 : 0 );
		int sbLen = sb.length();
		int excess = sbLen - tab1 + 1;
		int shift2 = excess - Math.max( 0, excess - shiftable );
		if( shift2 > 0 ) {
			rightStr = rightStr.substring( shift2, rightStr.length() );
			excess -= shift2;
		}
		if( excess > 0 ) {
			switch( excess ) {
			case 1:	sb.delete( sbLen - excess, sbLen ); break;
			case 2: sb.delete( sbLen - excess - 2 , sbLen ); sb.append( ".." ); break;
			default: sb.delete( sbLen - excess - 3, sbLen ); sb.append( "..." ); break;
			}
		}
		String strLineItem = String.format( "%-" + ( tab1 - 1 ) + "s%s", sb.toString(), rightStr );
		sb.setLength( 0 );
		sb.append( strLineItem );
		return sb;
	}

	@Override
	public String splitName(Customer customer, String name) {
		if (name.contains(", ")) {
			String[] namen = name.split(", ");
			customer.setFirstName(namen[1].trim());
			customer.setLastName(namen[0].trim());
		} else {
			String[] namen = name.split(" ");
			int namenLength = namen.length;
			String firstNames= "";
			for (int i=0; i < namenLength-1; i++) {
				firstNames += namen[i];
				if (i < namenLength-2) {
					firstNames += " ";
				}
			}
			customer.setFirstName(firstNames.trim());
			customer.setLastName(namen[namenLength-1].trim());
		}
		return singleName(customer);
	}

	@Override
	public String singleName(Customer customer) {
		String ausgabe = "";
		ausgabe += customer.getLastName();
		ausgabe += ", ";
		ausgabe += customer.getFirstName();
		return ausgabe;
	}
}