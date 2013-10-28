/*
 * Copyright (c) 2013 Grid Dynamics International, Inc. All Rights Reserved
 * http://www.griddynamics.com
 *
 * IMDG Workshop is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * $Id: $
 * @Project:     IMDG Workshop
 * @Description: Demo application for IMDG based on Oracle Coherence
 */

package com.griddynamics.workshop.imdg.domain.common.load;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mmyslyvtsev@griddynamics.com
 * @since 10/7/13
 */
public abstract class DelegatingLoader<T> {

    private Loader<T> delegate;

    protected Logger log;

    protected DelegatingLoader(Loader<T> delegate) {
        this.delegate = delegate;
        this.log = LoggerFactory.getLogger(getRootDelegate().getClass());
    }

    public Loader<T> getDelegate() {
        return delegate;
    }

    public Loader getRootDelegate() {
        Loader result = delegate;
        while (result instanceof DelegatingLoader) {
            result = ((DelegatingLoader) result).getDelegate();
        }
        return result;
    }
}
