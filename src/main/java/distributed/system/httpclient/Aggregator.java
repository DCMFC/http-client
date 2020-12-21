package distributed.system.httpclient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import distributed.system.httpclient.WebClient;

public class Aggregator {
	
	private WebClient webClient;
	
	public Aggregator() {
		this.webClient = new WebClient();
	}
	
	public List<String> sendTaksToWorkers(List<String> workersAddresses, List<String> tasks){
		@SuppressWarnings("unchecked")
		CompletableFuture<String> [] futures = new CompletableFuture[workersAddresses.size()];
		
		for(int i=0; i < workersAddresses.size(); i++) {
			String workerAddress = workersAddresses.get(i);
			String task = tasks.get(i);
			
			byte [] requestPayload = task.getBytes();
			futures[i] = webClient.sendTask(workerAddress, requestPayload);
		}
		
		List<String> results = new ArrayList<>();
		for(int i=0; i < tasks.size(); i++) {
			results.add(futures[i].join());
		}
		
		return results;
	}

}
