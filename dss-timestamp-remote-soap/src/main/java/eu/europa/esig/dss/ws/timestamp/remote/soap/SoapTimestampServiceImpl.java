package eu.europa.esig.dss.ws.timestamp.remote.soap;

import eu.europa.esig.dss.ws.dto.DigestDTO;
import eu.europa.esig.dss.ws.timestamp.dto.TimestampResponseDTO;
import eu.europa.esig.dss.ws.timestamp.remote.RemoteTimestampService;
import eu.europa.esig.dss.ws.timestamp.remote.soap.client.SoapTimestampService;

public class SoapTimestampServiceImpl implements SoapTimestampService {
	
	private static final long serialVersionUID = 7421969260893851663L;
	
	private RemoteTimestampService timestampService;
	
	public void setTimestampService(RemoteTimestampService timestampService) {
		this.timestampService = timestampService;
	}

	@Override
	public TimestampResponseDTO getTimestampResponse(DigestDTO digest) {
		return timestampService.getTimestampResponse(digest.getAlgorithm(), digest.getValue());
	}

}
