package org.springframework.social.flickr.api.impl;

import org.springframework.social.flickr.api.ContentTypeEnum;
import org.springframework.social.flickr.api.Perms;
import org.springframework.social.flickr.api.Photo;
import org.springframework.social.flickr.api.PhotoDetail;
import org.springframework.social.flickr.api.PhotoOperations;
import org.springframework.social.flickr.api.Photos;
import org.springframework.social.flickr.api.Sizes;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class PhotoTemplate extends AbstractFlickrOperations implements PhotoOperations {
	private final RestTemplate restTemplate;
	
	public PhotoTemplate(RestTemplate restTemplate,boolean isAuthorizedForUser) {
		super(isAuthorizedForUser);
		this.restTemplate = restTemplate;
	}
	
	@Override
	public boolean addTags(String photoId, String[] tagList) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("photo_id", photoId);
		parameters.set("tags", toCommaList(tagList));
		restTemplate.postForObject(buildUri("flickr.photos.addTags"), parameters, Object.class);
		return true;
	}
	
	@Override
	public boolean addTags(String photoId, String tags) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("photo_id", photoId);
		parameters.set("tags", tags);
		restTemplate.postForObject(buildUri("flickr.photos.addTags"), parameters, Object.class);
		return true;
	}
	

	@Override
	public boolean removeTag(String tagId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("tag_id", tagId);
		restTemplate.postForObject(buildUri("flickr.photos.removeTag"), parameters, Object.class);
		return true;
	}
	
	@Override
	public boolean delete(String photoId) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("photo_id", photoId);
		restTemplate.postForObject(buildUri("flickr.photos.delete"), parameters, Object.class);
		return true;
	}
	
	@Override
	public Photos getRecent(String perPage, String page, String[] extras) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("per_page", perPage);
		parameters.set("page", page);
		parameters.set("extras", toCommaList(extras));
		return restTemplate.getForObject(buildUri("flickr.photos.getRecent",parameters), Photos.class);		 
	}
	
	@Override
	public Photo getFavorites(String perPage, String page, String photoId) {
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("per_page", perPage);
		parameters.set("page", page);
		parameters.set("photo_id", photoId);
		return restTemplate.getForObject(buildUri("flickr.photos.getFavorites",parameters), Photo.class);	
	}
	
	@Override
	public PhotoDetail getInfo(String photoId) {
		return restTemplate.getForObject(buildUri("flickr.photos.getInfo","photo_id",photoId), PhotoDetail.class);	
	}


	@Override
	public Sizes getSizes(String photoId) {
		return restTemplate.getForObject(buildUri("flickr.photos.getSizes","photo_id",photoId), Sizes.class);	
	}
	
	@Override
	public Perms getPerms(String photoId) {
		requireAuthorization();
		return restTemplate.getForObject(buildUri("flickr.photos.getPerms","photo_id",photoId), Perms.class);	
	}

	@Override
	public boolean setContentType(String photoId, ContentTypeEnum contentTypeEnum) {
		requireAuthorization();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
		parameters.set("photo_id", photoId);
		parameters.set("content_type", contentTypeEnum.getType());
		restTemplate.postForObject(buildUri("flickr.photos.setContentType"), parameters, Object.class);
		return true;
	}

	
	
	private String toCommaList(String[] a){
		if (a == null)
            return "null";
	int iMax = a.length - 1;
	if (iMax == -1)
            return "";

        StringBuilder b = new StringBuilder();
        
        for (int i = 0; ; i++) {
            b.append(a[i]);
	    if (i == iMax)
		return b.toString();
            b.append(",");
        }
	}









}
