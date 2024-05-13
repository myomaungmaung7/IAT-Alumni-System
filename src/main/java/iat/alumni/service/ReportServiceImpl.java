package iat.alumni.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import iat.alumni.model.Article;
import iat.alumni.model.Report;
import iat.alumni.model.ReportDto;
import iat.alumni.model.User;
import iat.alumni.repository.ArticleRepository;
import iat.alumni.repository.ReportRepository;
import iat.alumni.repository.UserRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService{
	
	@Autowired
	private ReportRepository reportRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ArticleRepository articleRepo;
	
	@Override
	public Report createReport(Report report) {
		return reportRepo.save(report);
	}

	@Override
	public List<ReportDto> getAllReport() {
		List<Report> reports=reportRepo.findAll();
		List<ReportDto> reportDtos=new ArrayList<>();
		for(Report report : reports) {
			ReportDto reportDto=new ReportDto();
			reportDto.setId(report.getReportId());
			reportDto.setReason(report.getReason());
			reportDto.setDate(report.getDate());
			Optional<User> user=userRepo.findById(report.getUser().getUserId());
			reportDto.setUserName(user.get().getUserName());
			Optional<Article> article=articleRepo.findById(report.getArticle().getArticleId());
			reportDto.setTitle(article.get().getTitle());
			reportDtos.add(reportDto);
		}
		return reportDtos;
	}

	@Override
	public void deletebyId(Integer reportId) {
		reportRepo.deleteById(reportId);
	}
}