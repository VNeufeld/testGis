package com.dev.gis.connector.joi.protocol;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name="money")
@XmlType(propOrder={ "amount", "currency" } )
public class MoneyAmount extends BasicProtocol implements Comparable<MoneyAmount>{

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

	
	
	@Override
	public int compareTo(MoneyAmount o) {
		if (! currency.equals(o.getCurrency()))
			throw new UnconvertibleMoneyAmountException ("conversion rate not available, cannot compare money amount");
		return amount.compareTo(o.amount);
	}

	public String getAmount () {
		String formattedString = df.format(amount);
		return formattedString;
	}
	
	public String getCurrency() {
		return currency;
	}
	
	@XmlTransient
	public BigDecimal getDecimalAmount() {
		return amount;
	}

	public void setAmount (String amount) {
		try {
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
	public boolean equals(Object obj) {
		// missing partner?
		if(obj == null)
			return false;

		// identical?
		if(this == obj)
			return true;

		// identical type?
		if( !(obj.getClass().equals(this.getClass())))
			return false;

		// all attributes have to match!
		MoneyAmount money = (MoneyAmount) obj;
		if (! this.currency.equals(money.currency)) return false;
		return this.getAmount().equals(money.getAmount());
	}

	@Override
	public String toString() {
		return df.format(amount) + " " + currency;
	}
}