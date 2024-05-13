package iat.alumni.service;

import java.util.List;

import iat.alumni.model.Report;
import iat.alumni.model.ReportDto;

public interface ReportService {
	Report createReport (Report report);
	List <ReportDto> getAllReport();
	void deletebyId (Integer reportId);
}
