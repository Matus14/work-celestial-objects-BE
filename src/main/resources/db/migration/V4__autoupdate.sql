
CREATE OR REPLACE FUNCTION public.set_updated_at()
RETURNS trigger
LANGUAGE plpgsql
AS $$
BEGIN
  NEW.updated_at := now();
  RETURN NEW;
END;
$$;

-- Drop old trigger if it exists
DROP TRIGGER IF EXISTS trg_set_updated_at ON public.celestial_object;

-- Create trigger
CREATE TRIGGER trg_set_updated_at
BEFORE UPDATE ON public.celestial_object
FOR EACH ROW
EXECUTE FUNCTION public.set_updated_at();