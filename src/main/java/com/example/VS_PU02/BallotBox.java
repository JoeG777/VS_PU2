package com.example.VS_PU02;

import com.example.VS_PU02.ElectionService;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.websocket.EncodeException;

public class BallotBox {
	private final Map<String, Integer> votes = new HashMap<>();
	private final Set<ElectionService> observers = new HashSet<>();
	private static BallotBox instance = new BallotBox();

	private BallotBox() {
		super();
	}

	public static BallotBox getInstance() {
		return BallotBox.instance;
	}


	public synchronized void addObserver(ElectionService observer) {
		this.observers.add(observer);
	}

	public synchronized void removeObserver(ElectionService observer) {
		this.observers.remove(observer);
	}

	public synchronized void vote(String choice) throws IOException, EncodeException {
		var voteCount = this.votes.get(choice);
		System.out.println("BallotBox: " + voteCount);
		if (voteCount == null) {
			voteCount = 0;
		}
		System.out.println("BallotBox good vote");
		this.votes.put(choice, voteCount + 1);
		System.out.println(observers.toString());
		for (ElectionService observer : this.observers) {
			observer.notify(this);
		}
		System.out.println("Done voting");
	}

	public synchronized int countVotes() {
		var sum = 0;
		for (var choice : this.votes.entrySet()) {
			sum += choice.getValue();
		}
		return sum;
	}


	public synchronized int getNumberOfVotes(String choice) {
		int voteCount = this.votes.get(choice);
		/*if (voteCount == null) {
			voteCount = 0;
		}*/
		return voteCount;
	}

	public synchronized Set<String> getChoices() {
		return this.votes.keySet();
	}
}
