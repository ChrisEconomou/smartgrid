package com.smartgrid.policies;

import com.smartgrid.app.Aggregator;
import java.util.Date;

public interface AggregatorPolicy {

	public String getPolicyName();

	public Double getPolicyVersion();

	public String getPolicyAuthor();
	
	public void tick(Date date, Aggregator aggregator);
	
	public Double getPrice();
	
	public Double getSupply();
}
