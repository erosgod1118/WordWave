package co.polarpublishing.common.util;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.polarpublishing.common.exception.DirectoryException;

public class ResourceDirectoryUtil {

	private static final Logger log = LoggerFactory.getLogger(ResourceDirectoryUtil.class);

	/**
	 * Create any directory (main use-case at the moment is creation of resource
	 * area directory)
	 *
	 * @param path path of the directory to be created
	 * @return true if directory was created successfully
	 * @throws DirectoryException in case if directory path is empty on in case of
	 *                            it will fail to create directory
	 */
	public boolean createDirectoryIfNotExist(String path) throws DirectoryException {
		if (null == path || path.isEmpty()) {
			throw new DirectoryException("directory to be created can not be null or empty");
		}

		log.info("trying to create a directory at path {} (if directory already exist then the request will be ignored)",
				path);
		File directory = new File(path);
		if (directory.exists()) {
			log.info("directory {} already exists (ignoring the request)", directory.getAbsolutePath());
		} else {
			if (directory.mkdirs()) {
				log.info("successfully created directory at {}", directory.getAbsolutePath());
			} else {
				throw new DirectoryException(
						String.format("failed to create directory at %s - please check log output and try again", path));
			}
		}

		return true;
	}

}
