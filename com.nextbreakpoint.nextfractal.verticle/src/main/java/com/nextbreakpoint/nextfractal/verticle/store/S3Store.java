package com.nextbreakpoint.nextfractal.verticle.store;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.nextbreakpoint.nextfractal.core.Bundle;
import com.nextbreakpoint.nextfractal.core.TileUtils;
import io.vertx.core.json.JsonObject;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class S3Store implements BundleStore {
    private static final String TYPE_APPLICATION_JSON = "application/json";

    private final AmazonS3 s3client;
    private final String bucketName;

    public S3Store(String accessKey, String secretKey, String region, String bucketName) {
        final BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        final AWSStaticCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
        this.s3client = AmazonS3Client.builder().withCredentials(credentialsProvider).withRegion(region).build();
        this.bucketName = bucketName;
    }

    @Override
    public Bundle getBundle(UUID uuid) {
        try {
            final String objectAsString = s3client.getObjectAsString(bucketName, uuid.toString());

            final JsonObject json = new JsonObject(objectAsString);

            final String manifest = json.getString("manifest");
            final String metadata = json.getString("metadata");
            final String script = json.getString("script");

            return TileUtils.parseData(manifest, metadata, script);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
    }

    @Override
    public void saveBundle(UUID uuid, byte[] bytes) {
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(TYPE_APPLICATION_JSON);
        metadata.setContentLength(bytes.length);

        final ByteArrayInputStream input = new ByteArrayInputStream(bytes);

        s3client.putObject(new PutObjectRequest(bucketName, uuid.toString(), input, metadata));
    }

    @Override
    public void deleteBundle(UUID uuid) {
        s3client.deleteObject(new DeleteObjectRequest(bucketName, uuid.toString()));
    }

    @Override
    public List<String> listBundles() {
        final ObjectListing objects = s3client.listObjects(bucketName);

        return objects.getObjectSummaries().stream().map(s -> s.getKey()).sorted().collect(Collectors.toList());
    }
}
