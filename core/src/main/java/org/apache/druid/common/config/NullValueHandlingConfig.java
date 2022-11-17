/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.druid.common.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.druid.java.util.common.logger.Logger;

public class NullValueHandlingConfig
{
  private static final Logger log = new Logger(NullValueHandlingConfig.class);
  public static final String NULL_HANDLING_CONFIG_STRING = "druid.generic.useDefaultValueForNull";

  //added to preserve backward compatibility
  //and not count nulls during cardinality aggrgation over strings

  public static final String NULL_HANDLING_DURING_STRING_CARDINALITY = "druid.generic.ignoreNullsForStringCardinality";

  @JsonProperty("useDefaultValueForNull")
  private final boolean useDefaultValuesForNull;

  @JsonProperty("ignoreNullsForStringCardinality")
  private final boolean ignoreNullsForStringCardinality;


  @JsonCreator
  public NullValueHandlingConfig(
      @JsonProperty("useDefaultValueForNull") Boolean useDefaultValuesForNull,
      @JsonProperty("ignoreNullsForStringCardinality") Boolean ignoreNullsForStringCardinality
  )
  {
    if (useDefaultValuesForNull == null) {
      
      this.useDefaultValuesForNull = Boolean.valueOf(System.getProperty(NULL_HANDLING_CONFIG_STRING, "true"));
    } else {
      this.useDefaultValuesForNull = useDefaultValuesForNull;
    }
    if (ignoreNullsForStringCardinality == null) {
      this.ignoreNullsForStringCardinality = Boolean.valueOf(System.getProperty(
          NULL_HANDLING_DURING_STRING_CARDINALITY,
          "false"
      ));
    } else {
      if (this.useDefaultValuesForNull) {
        this.ignoreNullsForStringCardinality = ignoreNullsForStringCardinality;
      } else {
        this.ignoreNullsForStringCardinality = false;
      }
    }
  }

  public boolean isIgnoreNullsForStringCardinality()
  {
    log.info("[CTEST][GET-PARAM] " + "druid.generic.ignoreNullsForStringCardinality");
    return ignoreNullsForStringCardinality;
  }

  public boolean isUseDefaultValuesForNull()
  {
    log.info("[CTEST][GET-PARAM] " + "druid.generic.useDefaultValueForNull");
    return useDefaultValuesForNull;
  }
}
