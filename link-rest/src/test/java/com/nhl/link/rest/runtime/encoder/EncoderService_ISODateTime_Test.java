package com.nhl.link.rest.runtime.encoder;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import com.nhl.link.rest.ResourceEntity;
import com.nhl.link.rest.encoder.Encoder;
import com.nhl.link.rest.encoder.EncoderFilter;
import com.nhl.link.rest.encoder.Encoders;
import com.nhl.link.rest.encoder.PropertyMetadataEncoder;
import com.nhl.link.rest.it.fixture.cayenne.iso.Java8ISODateTestEntity;
import com.nhl.link.rest.it.fixture.cayenne.iso.Java8ISOOffsetDateTimeTestEntity;
import com.nhl.link.rest.it.fixture.cayenne.iso.Java8ISOTimeTestEntity;
import com.nhl.link.rest.it.fixture.cayenne.iso.Java8ISOTimestampTestEntity;
import com.nhl.link.rest.runtime.semantics.RelationshipMapper;
import com.nhl.link.rest.unit.Java8TestWithCayenneMapping;

public class EncoderService_ISODateTime_Test extends Java8TestWithCayenneMapping {

    private EncoderService encoderService;

    @Before
    public void before() {
        IAttributeEncoderFactory attributeEncoderFactory = new AttributeEncoderFactoryProvider(Collections.emptyMap()).get();
        IStringConverterFactory stringConverterFactory = mock(IStringConverterFactory.class);

        encoderService = new EncoderService(Collections.<EncoderFilter>emptyList(), attributeEncoderFactory, stringConverterFactory,
                new RelationshipMapper(), Collections.<String, PropertyMetadataEncoder> emptyMap());
    }

    @Test
    public void testJava8ISODate() {
        ResourceEntity<Java8ISODateTestEntity> resourceEntity = getResourceEntity(Java8ISODateTestEntity.class);
        appendPersistenceAttribute(resourceEntity, Java8ISODateTestEntity.DATE, LocalDate.class, Types.DATE);

        LocalDate localDate = LocalDate.now();

        Java8ISODateTestEntity isoDateTestEntity = new Java8ISODateTestEntity();
        isoDateTestEntity.setDate(localDate);

        String dateString = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);

        assertEquals("{\"data\":[{\"date\":\"" + dateString + "\"}],\"total\":1}",
                toJson(isoDateTestEntity, resourceEntity));
    }

    @Test
    public void testJava8ISOTime() {
        // fractional part is not printed, when less than a millisecond
        _testJava8ISOTime(LocalTime.of(10, 0, 0), "HH:mm:ss");
        _testJava8ISOTime(LocalTime.of(10, 0, 0, 1), "HH:mm:ss");
        _testJava8ISOTime(LocalTime.of(10, 0, 0, 999_999), "HH:mm:ss");
        int millisecond = 1_000_000; // millisecond is 10^6 nanoseconds
        _testJava8ISOTime(LocalTime.of(10, 0, 0, millisecond), "HH:mm:ss.SSS");
    }

    private void _testJava8ISOTime(LocalTime time, String expectedPattern) {

        ResourceEntity<Java8ISOTimeTestEntity> resourceEntity = getResourceEntity(Java8ISOTimeTestEntity.class);
        appendPersistenceAttribute(resourceEntity, Java8ISOTimeTestEntity.TIME, LocalTime.class, Types.TIME);

        Java8ISOTimeTestEntity isoTimeTestEntity = new Java8ISOTimeTestEntity();
        isoTimeTestEntity.setTime(time);

        String timeString = DateTimeFormatter.ofPattern(expectedPattern).format(time);

        assertEquals("{\"data\":[{\"time\":\"" + timeString + "\"}],\"total\":1}",
                toJson(isoTimeTestEntity, resourceEntity));
    }

    @Test
    public void testJava8ISOTimestamp() {
        // fractional part is not printed, when less than a millisecond
        _testJava8ISOTimestamp(LocalDateTime.of(2017, 1, 1, 10, 0, 0), "yyyy-MM-dd'T'HH:mm:ss");
        _testJava8ISOTimestamp(LocalDateTime.of(2017, 1, 1, 10, 0, 0, 1), "yyyy-MM-dd'T'HH:mm:ss");
        _testJava8ISOTimestamp(LocalDateTime.of(2017, 1, 1, 10, 0, 0, 999_999), "yyyy-MM-dd'T'HH:mm:ss");
        int millisecond = 1_000_000; // millisecond is 10^6 nanoseconds
        _testJava8ISOTimestamp(LocalDateTime.of(2017, 1, 1, 10, 0, 0, millisecond), "yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    private void _testJava8ISOTimestamp(LocalDateTime dateTime, String expectedPattern) {

        ResourceEntity<Java8ISOTimestampTestEntity> resourceEntity = getResourceEntity(Java8ISOTimestampTestEntity.class);
        appendPersistenceAttribute(resourceEntity, Java8ISOTimestampTestEntity.TIMESTAMP, LocalDateTime.class, Types.TIMESTAMP);

        Java8ISOTimestampTestEntity isoTimestampTestEntity = new Java8ISOTimestampTestEntity();
        isoTimestampTestEntity.setTimestamp(dateTime);

        String dateTimeString = DateTimeFormatter.ofPattern(expectedPattern).format(dateTime);

        assertEquals("{\"data\":[{\"timestamp\":\"" + dateTimeString + "\"}],\"total\":1}",
                toJson(isoTimestampTestEntity, resourceEntity));
    }
    
    @Test
    public void testJava8ISOOffsetDateTime() {
        // fractional part is not printed, when less than a millisecond
        _testJava8ISOOffsetDateTime(OffsetDateTime.of(LocalDateTime.of(2017, 1, 1, 10, 0, 0), ZoneOffset.ofHours(3)));
        _testJava8ISOOffsetDateTime(OffsetDateTime.of(LocalDateTime.of(2017, 1, 1, 10, 0, 0, 1), ZoneOffset.ofHours(3)));
        _testJava8ISOOffsetDateTime(OffsetDateTime.of(LocalDateTime.of(2017, 1, 1, 10, 0, 0, 999_999), ZoneOffset.ofHours(3)));
        int millisecond = 1_000_000; // millisecond is 10^6 nanoseconds
        _testJava8ISOOffsetDateTime(OffsetDateTime.of(LocalDateTime.of(2017, 1, 1, 10, 0, 0, millisecond), ZoneOffset.ofHours(3)));
    }
    
    private void _testJava8ISOOffsetDateTime(OffsetDateTime dateTime) {

        ResourceEntity<Java8ISOOffsetDateTimeTestEntity> resourceEntity = getResourceEntity(Java8ISOOffsetDateTimeTestEntity.class);
        appendPersistenceAttribute(resourceEntity, Java8ISOOffsetDateTimeTestEntity.TIMESTAMP, OffsetDateTime.class, Types.VARCHAR);

        Java8ISOOffsetDateTimeTestEntity isoOffsetDateTimeTestEntity = new Java8ISOOffsetDateTimeTestEntity();
        isoOffsetDateTimeTestEntity.setTimestamp(dateTime);

        String dateTimeString = DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(dateTime);

        assertEquals("{\"data\":[{\"timestamp\":\"" + dateTimeString + "\"}],\"total\":1}",
                toJson(isoOffsetDateTimeTestEntity, resourceEntity));
    }

    private String toJson(Object object, ResourceEntity<?> resourceEntity) {
        Encoder encoder = encoderService.dataEncoder(resourceEntity);
        return Encoders.toJson(encoder, Collections.singletonList(object));
    }
}