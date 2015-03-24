package com.nbb.apps.carreservationv2.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

public class MoneyAmount extends BasicProtocol implements Comparable<MoneyAmount>, Serializable {

	private static final long serialVersionUID = 4918397835320353956L;

	private String currency;

	private BigDecimal amount;

	private static DecimalFormat df;
	
	
	{
		df = (DecimalFormat) DecimalFormat.getNumberInstance(Locale.GERMANY);
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		df.setParseBigDecimal(true);
	}

	
	
	public MoneyAmount() {
		// empty
	}
	
	public MoneyAmount(BigDecimal amount, String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	public MoneyAmount(String amount, String currency) {
		setAmount(amount);
		this.currency = currency;
	}
	
	public static MoneyAmount add(MoneyAmount value1, MoneyAmount value2) {
		if ( value1.amount != null && value2.amount != null)
		{
			BigDecimal v = value1.amount.add(value2.amount);
			return new MoneyAmount(v,value1.getCurrency());
		}
		return null;
	}
	
	public static MoneyAmount subtract(MoneyAmount value1, MoneyAmount value2) {
		if ( value1.amount != null && value2.amount != null)
		{
			BigDecimal v = value1.amount.subtract(value2.amount);
			return new MoneyAmount(v,value1.getCurrency());
		}
		return null;
	}

	public static MoneyAmount divide(MoneyAmount value, double deviser_value) {
		if ( value.amount != null )
		{
			BigDecimal devisor = BigDecimal.valueOf(deviser_value);
			
			int scale = 2;
			BigDecimal result = value.amount.divide(devisor,scale, RoundingMode.HALF_DOWN);

			return new MoneyAmount(result,value.getCurrency());
		}
		return null;
	}
	
	
	@Override
	public int compareTo(MoneyAmount o) {
		if (! currency.equals(o.getCurrency()))
			throw new UnconvertibleMoneyAmountException ("conversion rate not available, cannot compare money amount");
		return amount.compareTo(o.amount);
	}

	public String getAmount () {
		if ( amount != null )
			return  df.format(amount);
		return null;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	public BigDecimal getDecimalAmount() {
		return amount;
	}

	public void setAmount (String amount) {
		try {
			if ( !isEmpty(amount))
				this.amount = (BigDecimal) df.parse(amount);
		} catch(ParseException e) {
			throw new NumberFormatException("wrong syntax of number: " + amount);
		}
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public void setDecimalAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public boolean greaterOrEqual(MoneyAmount right) {
		if ( ! this.currency.equals(right.currency))
			throw new UnconvertibleMoneyAmountException("could not convert from " + this.currency + " to " + right.currency);
		return this.amount.compareTo(right.amount) >= 0;
	}

	public boolean lessThan(MoneyAmount right) {
		if ( ! this.currency.equals(right.currency))
			throw new UnconvertibleMoneyAmountException("could not convert from " + this.currency + " to " + right.currency);
		return this.amount.compareTo(right.amount) < 0;
	}

	@Override
	public String toString() {
		if ( amount == null)
			return "null";
		return  amount.toString() + " " + currency;
	}
}