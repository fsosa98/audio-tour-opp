package hr.fer.opp.projekt.audioVodic.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import hr.fer.opp.projekt.audioVodic.model.Statistics;
import hr.fer.opp.projekt.audioVodic.repository.StatisticsRepository;

@Service
@Transactional
public class StatisticsService {

	private final StatisticsRepository statisticsRepository;

	public StatisticsService(StatisticsRepository statisticsRepository) {
		this.statisticsRepository = statisticsRepository;
	}

	public Statistics getStatisticsForOnePage(HttpServletRequest req) {
		return new Statistics();
	}

	public List<Statistics> getStatisticsForAllPages(HttpServletRequest req) {
		return new ArrayList<Statistics>();
	}

	public void addStatistics(Statistics statistics) {
		statisticsRepository.save(statistics);
	}

	public void deleteStatistics(Statistics statistics) {
		statisticsRepository.delete(statistics);
	}

}
