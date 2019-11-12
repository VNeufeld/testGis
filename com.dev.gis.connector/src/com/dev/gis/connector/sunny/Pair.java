package com.dev.gis.connector.sunny;

public class Pair<L,R> {
    public  L key;
    public  R value;

    public Pair() {
    }

    public Pair(L key, R value) {
      this.key = key;
      this.value = value;
    }

    public static <L,R> Pair<L,R> of(L key, R value){
        return new Pair<L,R>(key, value);
    }

	public L getKey() {
		return key;
	}

	public void setKey(L key) {
		this.key = key;
	}

	public R getValue() {
		return value;
	}

	public void setValue(R value) {
		this.value = value;
	}
}
