package ru.iitp.proling.features;

import com.google.common.base.Objects;

public class Values {
	public static final class Const implements Value {
		final Object value;
		
		public Const(Object value) {
			this.value = value;
		}
		
		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#get()
		 */
		@Override
		public Object get() {
			return value;
		}
		
		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#isNull()
		 */
		@Override
		public boolean isNull() {
			return value == null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Const))
				return false;
			Const other = (Const) obj;
			return Objects.equal(other.value, value);
		}
		
		@Override
		public String toString() {
			return value.toString();
		}

	}


	public static class Var implements Value.Mutable {
		Object value;

		public Var() {
		}

		public Var(Object value) {
			this.value = value;
		}

		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#get()
		 */
		@Override
		public Object get() {
			return value;
		}

		@Override
		public void set(Object value) {
			this.value = value;
		}

		/* (non-Javadoc)
		 * @see ru.iitp.proling.features.Value#isNull()
		 */
		@Override
		public boolean isNull() {
			return value == null;
		}

		@Override
		public void clear() {
			value = null;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Var))
				return false;
			Var other = (Var) obj;
			return Objects.equal(other.value, value);
		}

		@Override
		public String toString() {
			return value.toString();
		}
	}

}
