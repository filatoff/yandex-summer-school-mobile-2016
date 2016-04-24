package ru.yandex.summerschool2016.filatovaa.data;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import ru.yandex.summerschool2016.filatovaa.data.BuildConfig;

/**
 * Base class for Robolectric data layer tests.
 * Inherit from this class to create a test.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, application = ApplicationStub.class, sdk=21)
public abstract class ApplicationTestCase {}
