package toppan.example.toppan.utils;


import static org.slf4j.LoggerFactory.getLogger;

import java.time.Instant;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.NonNull;

import org.slf4j.Logger;


public final class SrxUtility {
    private static final Logger LOG = getLogger(SrxUtility.class);
    private static final Set<String> availableZoneIds = ZoneId.getAvailableZoneIds();
    private static final String USA = "USA";
    private static final String USD = "USD";
    private static final String CAD = "CAD";
    private static final long ASCII_CODE_A = 0x41;
    private static final long NUM_COIL_COLUMN = 10;
    private static final String PLUS_MERGED = "+merged";

    private SrxUtility() {
        throw new UnsupportedOperationException("Can't instantiate utility class");
    }

    public static Map<ZoneId, ZonedDateTime> getMapOfZonesWithLocalTime(
            final LocalTime localTime, final Instant executionTime) {

        final LocalTime expectedTime =
                localTime != null
                        ? localTime.truncatedTo(ChronoUnit.MINUTES)
                        : LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        final Instant forUtcTime =
                executionTime != null
                        ? executionTime.truncatedTo(ChronoUnit.MINUTES)
                        : Instant.now().truncatedTo(ChronoUnit.MINUTES);

        LOG.info("Get time zone with time:{}, execution time: {}", expectedTime, forUtcTime);

        final Map<ZoneId, ZonedDateTime> result = new HashMap<>();
        for (String zoneId : availableZoneIds) {
            final ZonedDateTime zonedDateTime = forUtcTime.atZone(ZoneId.of(zoneId));
            if (zonedDateTime.getHour() == expectedTime.getHour()
                    && zonedDateTime.getMinute() == expectedTime.getMinute()) {
                result.putIfAbsent(ZoneId.of(zoneId), zonedDateTime);
                LOG.trace("Found zone: {}, with time: {}", ZoneId.of(zoneId), zonedDateTime);
            }
        }

        LOG.info("Found: {} zones with time:[{}]", result.size(), expectedTime);
        return result;
    }

    public static Set<ZoneId> getSetOfZonesWithLocalTime(
            final LocalTime localTime, final Instant executionTime) {

        final LocalTime expectedTime =
                localTime != null
                        ? localTime.truncatedTo(ChronoUnit.MINUTES)
                        : LocalTime.now().truncatedTo(ChronoUnit.MINUTES);

        final Instant forUtcTime =
                executionTime != null
                        ? executionTime.truncatedTo(ChronoUnit.MINUTES)
                        : Instant.now().truncatedTo(ChronoUnit.MINUTES);

        LOG.info("Get time zone with time:{}, execution time: {}", expectedTime, forUtcTime);

        final Set<ZoneId> result = new HashSet<>();
        for (String zoneId : availableZoneIds) {
            final ZonedDateTime zonedDateTime = forUtcTime.atZone(ZoneId.of(zoneId));
            // dayOfWeek here can be in the past, because shifted by TZ
            if (zonedDateTime.toLocalTime().equals(expectedTime)) {
                //          && zonedDateTime.getDayOfWeek().equals(dayOfWeek)) {
                result.add(ZoneId.of(zoneId));
                LOG.trace(
                        "Found zone: {}, with time: {}, day: {}",
                        ZoneId.of(zoneId),
                        zonedDateTime,
                        zonedDateTime.getDayOfWeek());
            }
        }

        LOG.info("Found: {} zones with time:[{}]", result.size(), expectedTime);
        return result;
    }

    public static Set<ZoneId> getSetOfZonesWithLocalTime(final LocalTime localTime) {
        return getSetOfZonesWithLocalTime(localTime, Instant.now());
    }

    public static String generateUuid() {
        return String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE));
    }

    public static String generateUuid(final long origin, final long bound) {
        return String.valueOf(ThreadLocalRandom.current().nextLong(origin, bound));
    }

//    public static String getStageSuffix(final String currentStage) {
//        if (currentStage.equalsIgnoreCase("dev")
//                || currentStage.equalsIgnoreCase("staging")
//                || currentStage.equalsIgnoreCase("test")) {
//            return currentStage;
//        }
//        return StringUtils.EMPTY;
//    }

//    public static String parseUnitOfMeasure(final String unitOfMeasure) {
//        if (isEmpty(unitOfMeasure)) {
//            return EMPTY;
//        }
//        try {
//            Long.parseLong(unitOfMeasure.trim());
//            return unitOfMeasure.trim();
//        } catch (final Exception ignore) {
//        }
//
//        LOG.debug("Try to get unit of measure for: [{}]", unitOfMeasure);
//
//        final String digitPattern = "[\\d\\s]";
//        final String characterPattern = "[\\D\\s]";
//
//        final String digits = unitOfMeasure.replaceAll(characterPattern, "");
//        final String characters = unitOfMeasure.replaceAll(digitPattern, "");
//        if (isEmpty(characters)) {
//            LOG.debug("No characters in [{}] returns EMPTY", unitOfMeasure);
//            return EMPTY;
//        }
//        final ProductUnitOfMeasure productUnitOfMeasure =
//                ProductUnitOfMeasure.getIfPresent(characters.trim());
//        if (productUnitOfMeasure == null) {
//            LOG.debug("Can not recognize unit of measure alias in [{}], returns EMPTY", unitOfMeasure);
//            return EMPTY;
//        }
//        if (isEmpty(digits)) {
//            LOG.debug(
//                    "No digits in unit of measure [{}], returns [{}]",
//                    unitOfMeasure,
//                    productUnitOfMeasure.getValue());
//            return String.valueOf(productUnitOfMeasure.getValue());
//        }
//        final Long result = Long.parseLong(digits.trim()) * productUnitOfMeasure.getValue();
//        LOG.debug("Transfer [{}] to [{}]", unitOfMeasure, result);
//        return String.valueOf(result);
//    }

//    public static Integer getUnitOfMeasureValueOrDefault(final String unitOfMeasure) {
//        try {
//            return getUnitOfMeasureValueOrThrowException(unitOfMeasure);
//        } catch (final Exception e) {
//            LOG.debug("For requested uom={} value was not found, returning default value", unitOfMeasure);
//            return 1;
//        }
//    }

    public static <T> Collection<List<T>> divideList(final List<T> list, final int divider) {
        final AtomicInteger counter = new AtomicInteger();
        return list.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / divider))
                .values();
    }

//    private static Integer getUnitOfMeasureValueOrThrowException(final String unitOfMeasure) {
//        if (isBlank(unitOfMeasure)) {
//            throw new BadRequestException("Product.price.unit.of.measure.empty");
//        }
//        final ProductUnitOfMeasure enumValue = ProductUnitOfMeasure.getIfPresent(unitOfMeasure);
//        if (enumValue != null) {
//            return enumValue.getValue();
//        }
//        try {
//            LOG.debug(
//                    "For requested uom: {} value in enum was not found, trying to round the number",
//                    unitOfMeasure);
//            return (int) Math.round(Double.parseDouble(unitOfMeasure));
//        } catch (NumberFormatException e) {
//            throw new UnitOfMeasureIncorrectValueException(unitOfMeasure);
//        }
//    }

    /**
     * Merges the provided maps and if duplicate entries are fount the sets are merges under the same
     * key.
     *
     * @param maps Maps to be merged.
     * @param <K> Key type.
     * @param <V> Value type.
     * @return Merged mutable map.
     */
    @NonNull
    @SafeVarargs
    public static <K, V> Map<K, Set<V>> mergeMapsOfSets(@NonNull final Map<K, Set<V>>... maps) {
        return Arrays.stream(maps)
                .flatMap(it -> it.entrySet().stream())
                .collect(
                        Collectors.toMap(
                                Map.Entry::getKey,
                                it -> new HashSet<>(it.getValue()),
                                (mutable, immutable) -> {
                                    mutable.addAll(immutable);
                                    return mutable;
                                }));
    }

    //** Calculates price, based on the {@link ProductPrice} price and measure value */
//    public static BigDecimal calculatePackagePrice(
//            final String unitOfMeasure, final BigDecimal price) {
//        final var measureValue = getUnitOfMeasureValueOrDefault(unitOfMeasure);
//        return measureValue > 1 ? MathUtils.divide(price, new BigDecimal(measureValue)) : price;
//    }

    public static <K, V> Map<K, Set<V>> toImmutableMapOfSets(final Map<K, Set<V>> map) {
        map.entrySet().forEach(it -> it.setValue(Collections.unmodifiableSet(it.getValue())));

        return Collections.unmodifiableMap(map);
    }

    public static List<String> getDistinctStringList(final List<String>... serialNumbers) {
        return Stream.of(serialNumbers)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());
    }

    /** Retrieves currencies from given ShipTos */
//    public static String getCurrenciesFromShipTos(final List<ShipTo> shipTos) {
//        final Set<String> countries =
//                shipTos
//                        .stream()
//                        .map(ShipTo::getDistributorCountry)
//                        .filter(Objects::nonNull)
//                        .collect(Collectors.toSet());
//        final StringBuilder currency = new StringBuilder();
//        for (String country : countries) {
//            currency.append(" ").append(USA.equals(country) ? USD : CAD);
//        }
//        return currency.toString().trim().replace(" ", "/");
//    }

    /**
     * Calculates packageConversion value. If value is not set - returns default package conversion
     */
//    public static Long calculatePackageConversion(final Long packageConversion) {
//        return isLongNotSpecified(packageConversion) ? DEFAULT_PACKAGE_CONVERSION : packageConversion;
//    }

    /** Checks whether long value isn't specified */
    public static boolean isLongNotSpecified(final Long value) {
        return value == null || value <= 0;
    }

//    @NonNull
//    public static BigDecimal getSingleItemPrice(
//            @Nullable final BigDecimal priceEach, @Nullable final Long packageConversion) {
//
//        if (packageConversion != null && packageConversion > 0) {
//
//            final BigDecimal priceEachOrZero = null == priceEach ? BigDecimal.ZERO : priceEach;
//
//            return priceEachOrZero.divide(BigDecimal.valueOf(packageConversion), RoundingMode.HALF_UP);
//        }
//
//        return BigDecimal.ZERO;
//    }

//    public static void validateTimeFrameOrThrowException(
//            final LocalDateTime from, final LocalDateTime to) {
//        if (from == null && to == null) {
//            return;
//        }
//        if (from == null || to == null) {
//            throw new ParameterMissingException("from or to");
//        }
//        if (from.isAfter(to)) {
//            throw new BadRequestException("Report.ondemand.dates.not.correct");
//        }
//    }

//    public static String coilDecode(final String coil) {
//        if (coil == null) {
//            throw new ParameterMissingException("coil");
//        }
//        final long row = coil.toUpperCase().subSequence(0, 1).charAt(0) - ASCII_CODE_A;
//        final String[] split = coil.split("-");
//        final String column = split[0].substring(1);
//        if (split.length > 1) {
//            return (row * NUM_COIL_COLUMN + Long.parseLong(column)) + PLUS_MERGED;
//        } else {
//            return String.valueOf(row * NUM_COIL_COLUMN + Long.parseLong(column));
//        }
//    }

//    @Nullable
//    public static List<Long> getIdsFromEntitiesList(
//            @Nullable final Collection<? extends BaseEntityWithStatus> entities) {
//        if (null == entities) {
//            return null;
//        }
//
//        return entities.stream().map(BaseEntity::getId).collect(Collectors.toUnmodifiableList());
//    }

    /**
     * Checks if erpOrderId in item from DB and ERP the same
     *
     * @param srxOrderErpOrderId srx order's erpOrderId
     * @param erpOrderErpOrderId erp order's erpOrderId
     * @return true if they are the same
     */
    public static boolean isErpOrderIdSame(
            final String srxOrderErpOrderId, final String erpOrderErpOrderId) {
        // TODO: We might need to take into account that in some ERP erpOrderId may have leading zeros
        // which we, probably, should ignore. See https://agilevision.atlassian.net/browse/SRX-21707
        final var erpOrderErpOrderIdAndSuffix =
                Arrays.asList(erpOrderErpOrderId.replaceFirst("^0+(?!$)", "").split("-"));
        final var srxOrderErpOrderIdAndSuffix =
                Arrays.asList(srxOrderErpOrderId.replaceFirst("^0+(?!$)", "").split("-"));

        // There is a situation possible when in DB we have erpOrderId = S1234, but in ERP response
        // we've got S1234-1 => the item should be updated
        if (srxOrderErpOrderIdAndSuffix.size() < erpOrderErpOrderIdAndSuffix.size()
                && srxOrderErpOrderIdAndSuffix
                .get(0)
                .equalsIgnoreCase(erpOrderErpOrderIdAndSuffix.get(0))) {
            return false;
        } else if (srxOrderErpOrderIdAndSuffix.size() >= 2 && erpOrderErpOrderIdAndSuffix.size() >= 2) {
            return erpOrderErpOrderIdAndSuffix.get(0).equalsIgnoreCase(srxOrderErpOrderIdAndSuffix.get(0))
                    && erpOrderErpOrderIdAndSuffix
                    .get(erpOrderErpOrderIdAndSuffix.size() - 1)
                    .equalsIgnoreCase(
                            srxOrderErpOrderIdAndSuffix.get(srxOrderErpOrderIdAndSuffix.size() - 1));
        }

        return srxOrderErpOrderIdAndSuffix.get(0).equalsIgnoreCase(erpOrderErpOrderIdAndSuffix.get(0));
    }
}
