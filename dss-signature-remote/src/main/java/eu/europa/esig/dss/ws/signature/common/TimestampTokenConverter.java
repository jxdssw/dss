package eu.europa.esig.dss.ws.signature.common;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.bouncycastle.cms.CMSException;
import org.bouncycastle.tsp.TSPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.validation.timestamp.TimestampInclude;
import eu.europa.esig.dss.validation.timestamp.TimestampToken;
import eu.europa.esig.dss.ws.dto.TimestampDTO;
import eu.europa.esig.dss.ws.dto.TimestampIncludeDTO;

public class TimestampTokenConverter {

	private static final Logger LOG = LoggerFactory.getLogger(TimestampTokenConverter.class);
	
	public static List<TimestampToken> toTimestampTokens(List<TimestampDTO> timestampDTOs) {
		List<TimestampToken> timestampTokens = new ArrayList<TimestampToken>();
		if (Utils.isCollectionNotEmpty(timestampDTOs)) {
			for (TimestampDTO timestampDTO : timestampDTOs) {
				if (timestampDTO != null && Utils.isArrayNotEmpty(timestampDTO.getBinaries())) {
					try {
						timestampTokens.add(toTimestampToken(timestampDTO));
					} catch (RemoteException e) {
						LOG.warn(e.getMessage());
					}
				}
			}
		}
		return timestampTokens;
	}
	
	private static TimestampToken toTimestampToken(TimestampDTO timestampDTO) throws RemoteException {
		try {
			TimestampToken timestampToken = new TimestampToken(timestampDTO.getBinaries(), timestampDTO.getType());
			timestampToken.setCanonicalizationMethod(timestampDTO.getCanonicalizationMethod());
			if (timestampDTO.getIncludes() != null) {
				timestampToken.setTimestampIncludes(toTimestampIncludes(timestampDTO.getIncludes()));
			}
			return timestampToken;
		} catch (TSPException | IOException | CMSException e) {
			throw new RemoteException(String.format("Cannot convert a TimestampDTO to TimestampToken class, reason : {}", e.getMessage()));
		}
	}
	
	private static List<TimestampInclude> toTimestampIncludes(List<TimestampIncludeDTO> timestampIncludeDTOs) {
		List<TimestampInclude> timestampIncludes = new ArrayList<TimestampInclude>();
		if (Utils.isCollectionNotEmpty(timestampIncludeDTOs)) {
			for (TimestampIncludeDTO timestampIncludeDTO : timestampIncludeDTOs) {
				if (timestampIncludeDTO != null) {
					timestampIncludes.add(new TimestampInclude(timestampIncludeDTO.getURI(), timestampIncludeDTO.isReferencedData()));
				}
			}
		}
		return timestampIncludes;
	}

}
