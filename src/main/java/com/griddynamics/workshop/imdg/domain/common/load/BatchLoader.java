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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author mmyslyvtsev@griddynamics.com
 * @since 10/1/13
 */
public class BatchLoader<T> extends DelegatingLoader<T> implements Loader<Collection<T>> {

    private final int batchSize;

    public BatchLoader(int batchSize, Loader<T> delegate) {
        super(delegate);
        this.batchSize = batchSize;
    }

    @Override
    public void load(Source source, final Callback<Collection<T>> callback) throws Exception {
        final List<T> batch = new ArrayList<T>(batchSize);
        getDelegate().load(source, new Callback<T>() {
            @Override
            public boolean onLoad(T entity) throws Exception {
                batch.add(entity);
                if (batch.size() < batchSize) {
                    return true;
                } else {
                    boolean result = callback.onLoad(batch);
                    batch.clear();
                    return result;
                }
            }
        });
        if (batch.size() > 0) {
            callback.onLoad(batch);
        }
    }
}
