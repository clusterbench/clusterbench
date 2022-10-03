/*
 * Copyright 2018 Radoslav Husár
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.test.clusterbench.web.jsf;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.FacesConfig;
import jakarta.inject.Named;

import org.jboss.test.clusterbench.common.SerialBean;

/**
 * @author Radoslav Husar
 */
@Named("jsfNamedCdiBean")
@SessionScoped
@FacesConfig(version = FacesConfig.Version.JSF_2_3)
public class JsfNamedCdiBean extends SerialBean {
}
