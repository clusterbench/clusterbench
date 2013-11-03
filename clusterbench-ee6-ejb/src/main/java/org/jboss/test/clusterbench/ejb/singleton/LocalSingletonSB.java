/*
 * Copyright 2013 Radoslav Husár
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

package org.jboss.test.clusterbench.ejb.singleton;

import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import org.jboss.test.clusterbench.common.ejb.CommonStatefulSBImpl;
// import org.jboss.ejb3.annotation.Clustered;

/**
 * Scope interface javax.enterprise.context.SessionScoped is not allowed on singleton enterprise beans. Only @Dependent is
 * allowed on singleton enterprise beans.
 *
 * @author Radoslav Husar
 * @version Dec 2011
 */
@Singleton
@LocalBean
// @Clustered -- JBAS014549: @Clustered annotation is currently not supported for singleton EJB.
public class LocalSingletonSB extends CommonStatefulSBImpl {
    // Inherit.
}
